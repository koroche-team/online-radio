package org.korocheteam.api.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.Song;

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

    public static SongDto from(Song song) {
        return SongDto.builder()
                .id(song.getId())
                .artist(song.getArtist())
                .title(song.getTitle())
                .album(song.getAlbum())
                .cover(song.getCover())
                .build();
    }
}
