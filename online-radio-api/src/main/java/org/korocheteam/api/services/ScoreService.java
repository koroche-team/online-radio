package org.korocheteam.api.services;

import org.korocheteam.api.models.dtos.requests.ScoreUpdateRequest;
import org.korocheteam.api.models.dtos.responses.ScoreUpdateResponse;

public interface ScoreService {
	ScoreUpdateResponse updateScore(ScoreUpdateRequest request);
}
