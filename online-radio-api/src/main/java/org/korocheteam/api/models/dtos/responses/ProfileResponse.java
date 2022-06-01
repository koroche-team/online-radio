package org.korocheteam.api.models.dtos.responses;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.dtos.AccountDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("response for profile")
public class ProfileResponse {
	private AccountDto profile;
}
