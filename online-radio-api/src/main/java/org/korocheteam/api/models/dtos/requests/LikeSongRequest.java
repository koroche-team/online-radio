package org.korocheteam.api.models.dtos.requests;

import lombok.Data;

@Data
public class LikeSongRequest {
	private String email;
	private Integer songId;
}
