package org.korocheteam.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.exceptions.AccountNotExistsException;
import org.korocheteam.api.models.Account;
import org.korocheteam.api.models.dtos.AccountDto;
import org.korocheteam.api.models.dtos.responses.LeaderboardResponse;
import org.korocheteam.api.models.dtos.responses.ProfileResponse;
import org.korocheteam.api.repositories.AccountsRepository;
import org.korocheteam.api.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountsRepository accountsRepository;

	@Override
	public LeaderboardResponse getAllAccountsSortedByScore() {
		List<Account> accounts = accountsRepository.findAll();
		accounts.sort(Comparator.comparingInt(Account::getScore));
		Collections.reverse(accounts);

		return LeaderboardResponse.builder()
				.accounts(AccountDto.from(accounts))
				.build();
	}

	@Override
	public ProfileResponse getProfileByNickname(String nickname) {
		Optional<Account> optionalAccount = accountsRepository.findByNickname(nickname);

		if (optionalAccount.isEmpty()) {
			throw new AccountNotExistsException("account with nickname " + nickname + " not exists");
		}

		return ProfileResponse.builder()
				.profile(AccountDto.from(optionalAccount.get()))
				.build();
	}
}
