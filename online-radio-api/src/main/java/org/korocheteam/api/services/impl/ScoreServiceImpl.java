package org.korocheteam.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.exceptions.AccountNotExistsException;
import org.korocheteam.api.models.Account;
import org.korocheteam.api.models.dtos.AccountDto;
import org.korocheteam.api.models.dtos.requests.ScoreUpdateRequest;
import org.korocheteam.api.models.dtos.responses.ScoreUpdateResponse;
import org.korocheteam.api.repositories.AccountsRepository;
import org.korocheteam.api.services.ScoreService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

	private final AccountsRepository accountsRepository;

	@Override
	public ScoreUpdateResponse updateScore(ScoreUpdateRequest request) {
		Optional<Account> accountOptional = accountsRepository.findByEmail(request.getEmail());
		Account account;
		if (accountOptional.isEmpty()) {
			throw new AccountNotExistsException("account with email " + request.getEmail() + " not exists");
		} else {
			account = accountOptional.get();
		}

		account.setScore(request.getMinutes());

		return ScoreUpdateResponse.builder()
				.account(AccountDto.from(accountsRepository.save(account)))
				.build();
	}
}
