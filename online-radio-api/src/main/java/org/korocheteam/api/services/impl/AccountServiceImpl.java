package org.korocheteam.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.Account;
import org.korocheteam.api.models.dtos.AccountDto;
import org.korocheteam.api.models.dtos.responses.LeaderboardResponse;
import org.korocheteam.api.repositories.AccountsRepository;
import org.korocheteam.api.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
}
