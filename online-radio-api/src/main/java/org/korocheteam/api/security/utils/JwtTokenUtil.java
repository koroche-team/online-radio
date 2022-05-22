package org.korocheteam.api.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.korocheteam.api.models.Account;
import org.korocheteam.api.models.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

	@Value("${spring.security.user.password}")
	private String SIGNING_KEY;

	@Value("${spring.security.user.token.time}")
	private Integer ACCESS_TOKEN_VALIDITY_TIME;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(SIGNING_KEY)
				.parseClaimsJws(token)
				.getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(Account account) {
		Claims claims = Jwts.claims().setSubject(account.getEmail());
		claims.put("scopes", List.of(new SimpleGrantedAuthority(account.getRole().name())));

		return Jwts.builder()
				.setClaims(claims)
				.setIssuer("online-radio")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_TIME))
				.signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
				.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		// TODO: make normal validation
		final String username = getUsernameFromToken(token);
		return (
				username.equals(userDetails.getUsername())
						&& !isTokenExpired(token));
	}
}
