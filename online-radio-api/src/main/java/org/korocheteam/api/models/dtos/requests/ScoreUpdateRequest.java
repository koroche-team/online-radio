package org.korocheteam.api.models.dtos.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("request for score updating")
public class ScoreUpdateRequest {
	@ApiModelProperty(value = "total minutes of listening", example = "10")
	private Integer minutes;

	@ApiModelProperty(value = "email of user which score need to update", example = "username@mail.ru")
	private String email;
}
