package org.korocheteam.api.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.korocheteam.api.repositories.AccountsRepository;

@RequiredArgsConstructor
@Service
public class AccountUserDetailsService implements UserDetailsService {

	private final AccountsRepository accountsRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return new AccountUserDetails(accountsRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found")));
	}
}
