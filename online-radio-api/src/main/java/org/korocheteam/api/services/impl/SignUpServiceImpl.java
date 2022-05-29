package org.korocheteam.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.exceptions.AccountAlreadyExistsException;
import org.korocheteam.api.models.Account;
import org.korocheteam.api.models.dtos.AccountDto;
import org.korocheteam.api.models.dtos.requests.SignUpRequest;
import org.korocheteam.api.models.dtos.responses.SignUpResponse;
import org.korocheteam.api.repositories.AccountsRepository;
import org.korocheteam.api.services.SignUpService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final AccountsRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse signUp(SignUpRequest signupRequest) {
        isAccountExists(signupRequest);

        Account account = Account.builder()
                .nickname(signupRequest.getNickname())
                .email(signupRequest.getEmail())
                .hashPassword(passwordEncoder.encode(signupRequest.getPassword()))
                .role(Account.Role.USER)
                .state(Account.State.CONFIRMED)
                .score(0)
                .build();


        return SignUpResponse.builder()
                .account(AccountDto.from(accountRepository.save(account)))
                .build();

    }

    private void isAccountExists(SignUpRequest signupRequest) {
        Optional<Account> accountByEmail = accountRepository.findByEmail(signupRequest.getEmail());
        Optional<Account> accountByNickname = accountRepository.findByNickname(signupRequest.getNickname());

        if (accountByEmail.isPresent()) {
            throw new AccountAlreadyExistsException("account with email " + signupRequest.getEmail() + " exists");
        }

        if (accountByNickname.isPresent()) {
            throw new AccountAlreadyExistsException("account with nickname " + signupRequest.getNickname() + " exists");
        }
    }
}
