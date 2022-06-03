package org.korocheteam.api.services;

import org.korocheteam.api.models.dtos.AccountDto;
import org.korocheteam.api.models.dtos.requests.ProfileRequest;
import org.korocheteam.api.models.dtos.responses.LeaderboardResponse;
import org.korocheteam.api.models.dtos.responses.ProfileResponse;

public interface AccountService {
	LeaderboardResponse getAllAccountsSortedByScore();
	ProfileResponse getProfileByNickname(String nickname);
	ProfileResponse getProfileByEmail(String email);
	ProfileResponse updateProfile(String email, ProfileRequest profileRequest);
}
