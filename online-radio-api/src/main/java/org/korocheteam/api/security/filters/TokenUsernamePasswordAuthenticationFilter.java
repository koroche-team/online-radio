package org.korocheteam.api.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.korocheteam.api.models.Account;
import org.korocheteam.api.models.dtos.requests.AuthRequest;
import org.korocheteam.api.models.dtos.responses.AuthResponse;
import org.korocheteam.api.repositories.AccountsRepository;
import org.korocheteam.api.security.details.AccountUserDetails;
import org.korocheteam.api.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class TokenUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountsRepository accountsRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthRequest authRequest = objectMapper.readValue(request.getReader(), AuthRequest.class);
            log.info("Attempting authentication: email {}", authRequest.getEmail());
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());

            return super.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AccountUserDetails userDetails = (AccountUserDetails) authResult.getPrincipal();
        Account account = userDetails.getAccount();

        account.setToken(jwtTokenUtil.generateToken(account));
        accountsRepository.save(account);

        AuthResponse authResponse = AuthResponse.builder()
                .token(account.getToken())
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), authResponse);
        chain.doFilter(request, response);
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
