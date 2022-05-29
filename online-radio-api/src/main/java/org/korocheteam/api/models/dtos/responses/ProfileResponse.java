package org.korocheteam.api.models.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.dtos.AccountDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {
	private AccountDto profile;
}
