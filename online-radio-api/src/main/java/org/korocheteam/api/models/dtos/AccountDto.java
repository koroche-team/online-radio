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

	public static AccountDto from(Account account) {
		return AccountDto.builder()
				.id(account.getId())
				.email(account.getEmail())
				.nickname(account.getNickname())
				.build();
	}
}
