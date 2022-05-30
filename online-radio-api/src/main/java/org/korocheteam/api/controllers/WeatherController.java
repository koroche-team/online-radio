package org.korocheteam.api.controllers;

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
public class WeatherController {

	@Autowired
	private WeatherClient weatherClient;

	@GetMapping("/{city}")
	public ResponseEntity<WeatherResponse> getWeatherOfCity(@PathVariable String city) {
		return ResponseEntity.ok(weatherClient.getWeatherByCity(city));
	}
}
