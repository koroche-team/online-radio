package org.korocheteam.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.exceptions.AccountNotExistsException;
import org.korocheteam.api.exceptions.SongAlreadyLikedException;
import org.korocheteam.api.exceptions.SongNotFoundException;
import org.korocheteam.api.models.Account;
import org.korocheteam.api.models.SongLike;
import org.korocheteam.api.models.Song;
import org.korocheteam.api.models.dtos.requests.LikeSongRequest;
import org.korocheteam.api.models.dtos.responses.LikeSongResponse;
import org.korocheteam.api.repositories.AccountsRepository;
import org.korocheteam.api.repositories.LikeRepository;
import org.korocheteam.api.repositories.SongRepository;
import org.korocheteam.api.services.LikeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

	private final LikeRepository likeRepository;

	private final SongRepository songRepository;

	private final AccountsRepository accountsRepository;

	@Override
	public LikeSongResponse likeSong(LikeSongRequest request) {
		Optional<SongLike> likeFromDB = likeRepository.findByAccountEmailAndSongId(request.getEmail(), request.getSongId());
		if (likeFromDB.isPresent()) {
			throw new SongAlreadyLikedException();
		}

		Optional<Song> songFromDB = songRepository.findById(request.getSongId());
		if (songFromDB.isEmpty()) {
			throw new SongNotFoundException();
		}

		Optional<Account> accountFromDB = accountsRepository.findByEmail(request.getEmail());
		if (accountFromDB.isEmpty()) {
			throw new AccountNotExistsException("account with email " + request.getEmail() + " not exists");
		}

		SongLike like = SongLike.builder()
				.song(songFromDB.get())
				.account(accountFromDB.get())
				.build();

		SongLike savedLike = likeRepository.save(like);

		songFromDB.get().getLikes().add(savedLike);

		return LikeSongResponse.builder()
				.amountOfLikes(songFromDB.get().getLikes().size())
				.build();
	}
}
