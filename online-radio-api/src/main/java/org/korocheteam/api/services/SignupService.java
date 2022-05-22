package org.korocheteam.api.services;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.Account;
import org.korocheteam.api.models.dtos.SignupRequest;
import org.korocheteam.api.models.dtos.SignupResponse;
import org.korocheteam.api.repositories.AccountsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SignupService {
    private final AccountsRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse signUp(SignupRequest signupRequest) {
        Account account = Account.builder()
                .nickname(signupRequest.getNickname())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(Account.Role.USER)
                .state(Account.State.CONFIRMED)
                .build();
        accountRepository.save(account);
        return SignupResponse.builder()
                .status("successful")
                .build();

    }
}
