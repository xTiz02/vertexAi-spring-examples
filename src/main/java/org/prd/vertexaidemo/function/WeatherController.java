package org.prd.vertexaidemo.function;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherChatService weatherService;

    public WeatherController(WeatherChatService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public String currentWeather(@RequestParam String message) {
        return weatherService.chat(message);
    }
}
