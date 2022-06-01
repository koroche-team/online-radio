package org.korocheteam.api.models.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.StreamStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("representation of audio stream status")
public class StreamStatusDto {
    @ApiModelProperty(value = "song itself")
    private SongDto songDto;

    @ApiModelProperty(value = "length of current song in millis")
    private Long length;

    @ApiModelProperty(value = "current time of song in millis")
    private Long time;

    public static StreamStatusDto from(StreamStatus status) {
        return StreamStatusDto.builder()
                .songDto(SongDto.from(status.getCurrentSong()))
                .time(status.getTime())
                .length(status.getLength())
                .build();
    }
}
