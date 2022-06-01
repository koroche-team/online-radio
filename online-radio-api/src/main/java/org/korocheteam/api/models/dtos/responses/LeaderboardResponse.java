package org.korocheteam.api.models.dtos.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.dtos.AccountDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel("representation of leaderboard")
public class LeaderboardResponse {
	@ApiModelProperty("list of users")
	List<AccountDto> accounts;
}
