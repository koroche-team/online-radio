package org.korocheteam.api.services;

import org.korocheteam.api.models.dtos.requests.LikeSongRequest;
import org.korocheteam.api.models.dtos.responses.LikeSongResponse;

public interface LikeService {
	LikeSongResponse likeSong(LikeSongRequest request);
}
