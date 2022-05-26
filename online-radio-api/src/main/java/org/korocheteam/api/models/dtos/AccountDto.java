package org.korocheteam.api.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
	private Long id;
	private String email;
	private String nickname;
	private String password;
	private String state;
	private String role;

	public static AccountDto from(Account account) {
		return AccountDto.builder()
				.password(account.getHashPassword())
				.id(account.getId())
				.email(account.getEmail())
				.nickname(account.getNickname())
				.build();
	}
}
