package org.korocheteam.api.services;

import org.korocheteam.api.models.dtos.responses.WeatherObject;
import org.korocheteam.api.models.dtos.responses.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WeatherClient {
	private final RestTemplate restTemplate = new RestTemplate();

	public WeatherResponse getWeatherByCity(String city) {
		WeatherObject response = restTemplate.getForObject("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=d64cde12deca23990a6e956bf65b16aa", WeatherObject.class);

		List<Object> weatherList = response.getWeather();
		Map map = (Map) weatherList.get(0);

		String main = (String) map.get("main");
		String description = (String) map.get("description");

		return WeatherResponse.builder()
				.description(description)
				.main(main)
				.build();
	}
}
