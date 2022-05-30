package org.korocheteam.api.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.korocheteam.api.security.utils.JwtTokenUtil;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.korocheteam.api.security.SecurityConfig.*;

@RequiredArgsConstructor
public class TokenAuthorizationFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			if (request.getRequestURI().startsWith(SIGNUP_URL) ||
					request.getRequestURI().startsWith(STATUS_URL) ||
					request.getRequestURI().startsWith(SWAGGER_URL) ||
					request.getRequestURI().startsWith(LOGIN_FILTER_PROCESS_URL)) {
				filterChain.doFilter(request, response);
				return;
			}

			String tokenHeader = request.getHeader("Authorization");
			String tokenString = tokenHeader.substring("Bearer ".length());
			UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(tokenString));

			// don't pass if token is invalid
			if (!jwtTokenUtil.validateToken(tokenString, userDetails)) {
				filterChain.doFilter(request, response);
				return;
			}

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (NullPointerException e) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			objectMapper.writeValue(response.getWriter(), Collections.singletonMap("error", "incorrect request format"));
		} catch (Exception e) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			objectMapper.writeValue(response.getWriter(), Collections.singletonMap("error", "user not found with token"));
		}
		filterChain.doFilter(request, response);
	}
}
