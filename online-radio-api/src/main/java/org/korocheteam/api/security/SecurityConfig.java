package org.korocheteam.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.korocheteam.api.repositories.AccountsRepository;
import org.korocheteam.api.security.details.AccountUserDetailsService;
import org.korocheteam.api.security.filters.TokenUsernamePasswordAuthenticationFilter;
import org.korocheteam.api.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.korocheteam.api.security.filters.TokenAuthorizationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String API_URL = "";

	public static final String LOGIN_FILTER_PROCESS_URL = API_URL + "/login";
	public static final String SIGNUP_URL = API_URL + "/signup";
	public static final String STATUS_URL = API_URL + "/status";
	public static final String SCORE_URL = API_URL + "/score";
	public static final String LEADERBOARD_URL = API_URL + "/leaderboard";
	public static final String PROFILE_URL = API_URL + "/profile";

	public static final String[] SWAGGER_ANT_URLS = {
			"/swagger-resources/**",
			"/configuration/**",
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/**"
	};

	public static final String[] SWAGGER_URLS = {
			"/swagger-resources/",
			"/configuration",
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/"
	};

	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Bean(name="myAuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and();

		http.exceptionHandling()
			.authenticationEntryPoint(
					(request, response, ex) -> response.sendError(
							HttpServletResponse.SC_UNAUTHORIZED,
							ex.getMessage()
					)
			)
			.and();

		http.authorizeRequests()
				.antMatchers(SIGNUP_URL).permitAll()
				.antMatchers(LOGIN_FILTER_PROCESS_URL).permitAll()
				.antMatchers("/status/**").permitAll()
				.antMatchers(SWAGGER_ANT_URLS).permitAll()
				.antMatchers(SCORE_URL).authenticated()
				.antMatchers(LEADERBOARD_URL).authenticated()
				.antMatchers(PROFILE_URL + "/**").authenticated()
				.antMatchers("/weather/**").authenticated()
				.antMatchers("/**").authenticated();


		UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new TokenUsernamePasswordAuthenticationFilter( objectMapper(), jwtTokenUtil, accountsRepository);
		usernamePasswordAuthenticationFilter.setFilterProcessesUrl(LOGIN_FILTER_PROCESS_URL);
		usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
		http.addFilter(usernamePasswordAuthenticationFilter);

		http.addFilterBefore(
				new TokenAuthorizationFilter(objectMapper(), jwtTokenUtil, userDetailsService()),
				TokenUsernamePasswordAuthenticationFilter.class
		);
	}

	@Bean
	public AccountUserDetailsService userDetailsService() {
		return new AccountUserDetailsService(accountsRepository);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
