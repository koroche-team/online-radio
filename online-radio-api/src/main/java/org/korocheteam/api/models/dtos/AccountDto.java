package org.korocheteam.api.models.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.Account;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("representation of account")
public class AccountDto {
	private Long id;
	private String email;
	private String nickname;
	private Integer score;

	public static AccountDto from(Account account) {
		return AccountDto.builder()
				.id(account.getId())
				.email(account.getEmail())
				.nickname(account.getNickname())
				.score(account.getScore())
				.build();
	}

	public static List<AccountDto> from(List<Account> accounts) {
		return accounts.stream()
				.map(AccountDto::from)
				.collect(Collectors.toList());
	}
}
