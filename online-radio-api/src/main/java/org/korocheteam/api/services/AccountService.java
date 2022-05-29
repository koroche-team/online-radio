package org.korocheteam.api.services;

import org.korocheteam.api.models.dtos.responses.LeaderboardResponse;

public interface AccountService {
	LeaderboardResponse getAllAccountsSortedByScore();
}
