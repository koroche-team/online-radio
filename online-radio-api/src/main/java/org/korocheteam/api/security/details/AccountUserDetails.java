package org.korocheteam.api.security.details;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Data
public class AccountUserDetails implements UserDetails {

	private final Account account;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = account.getRole().name();
		GrantedAuthority authority = new SimpleGrantedAuthority(role);

		return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() {
		return account.getHashPassword();
	}

	@Override
	public String getUsername() {
		return account.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return account.getState().equals(Account.State.CONFIRMED);
	}
}
