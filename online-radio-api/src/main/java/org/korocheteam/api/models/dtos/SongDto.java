package org.korocheteam.api.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.Song;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDto {
    // TODO: add validation
    private Integer id;

    private String artist;

    private String title;

    private String album;

    private String cover;

    private Integer amountOfLikes;

    public static SongDto from(Song song) {
        return SongDto.builder()
                .id(song.getId())
                .artist(song.getArtist())
                .title(song.getTitle())
                .album(song.getAlbum())
                .cover(song.getCover())
                .amountOfLikes(song.getLikes().size())
                .build();
    }

    public static List<SongDto> from(List<Song> songs) {
        return songs.stream()
                .map(SongDto::from)
                .collect(Collectors.toList());
    }
}
