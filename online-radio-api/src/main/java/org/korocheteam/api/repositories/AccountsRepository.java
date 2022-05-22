package org.korocheteam.api.repositories;

import org.korocheteam.api.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, String> {
	Optional<Account> findByEmail(String s);
	Optional<Account> findByToken(String token);
}
