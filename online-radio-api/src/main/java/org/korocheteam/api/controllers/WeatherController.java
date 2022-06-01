package org.korocheteam.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.korocheteam.api.models.dtos.StreamStatusDto;
import org.korocheteam.api.models.dtos.responses.WeatherResponse;
import org.korocheteam.api.services.WeatherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@Api("endpoint for getting weather")
public class WeatherController {

	@Autowired
	private WeatherClient weatherClient;

	@ApiOperation("returns weather by city(must contain token in header)")
	@ApiResponse(code = 200, message = "returns weather of specified city", response = WeatherResponse.class)
	@ApiImplicitParam(name = "city", required = true, dataType = "String",
			paramType = "path", value = "param to get weather of this city")
	@GetMapping("/{city}")
	public ResponseEntity<WeatherResponse> getWeatherOfCity(@PathVariable String city) {
		return ResponseEntity.ok(weatherClient.getWeatherByCity(city));
	}
}
