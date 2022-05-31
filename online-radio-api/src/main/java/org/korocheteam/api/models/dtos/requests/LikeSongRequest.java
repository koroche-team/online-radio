package org.korocheteam.api.models.dtos.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LikeSongRequest {
	@NotNull
	private String email;
	@NotNull
	private Integer songId;
}
