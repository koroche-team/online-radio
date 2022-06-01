package org.korocheteam.api.models.dtos.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("request for song liking")
public class LikeSongRequest {

	@ApiModelProperty(value = "email of user that likes", example = "username@mail.ru")
	@NotBlank
	private String email;

	@ApiModelProperty(value = "id of song", example = "1")
	@NotBlank
	private Integer songId;
}
