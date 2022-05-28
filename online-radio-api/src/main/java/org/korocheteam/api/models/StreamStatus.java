package org.korocheteam.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreamStatus {
    private Song currentSong;
    private Integer time;
    private Integer activeListeners;
}
