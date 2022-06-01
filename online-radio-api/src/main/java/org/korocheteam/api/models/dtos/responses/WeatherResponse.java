package org.korocheteam.api.models.dtos.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("representation of weather")
public class WeatherResponse {
	@ApiModelProperty(value = "weather in general")
	private String main;

	@ApiModelProperty(value = "some features of weather")
	private String description;
}
