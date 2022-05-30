package org.korocheteam.api.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.StreamStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreamStatusDto {
    private SongDto songDto;
    private Long length;
    private Long time;

    public static StreamStatusDto from(StreamStatus status) {
        return StreamStatusDto.builder()
                .songDto(SongDto.from(status.getCurrentSong()))
                .time(status.getTime())
                .length(status.getLength())
                .build();
    }
}
