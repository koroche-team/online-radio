package org.korocheteam.api.models.dtos.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.dtos.SongDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("representation of top songs")
public class TopSongsResponse {
	@ApiModelProperty(value = "list of top songs")
	private List<SongDto> songs;
}
