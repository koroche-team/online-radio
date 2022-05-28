package org.korocheteam.api.models.dtos.requests;

import lombok.Data;

@Data
public class ScoreUpdateRequest {
	private Integer minutes;
	private String email;
}
