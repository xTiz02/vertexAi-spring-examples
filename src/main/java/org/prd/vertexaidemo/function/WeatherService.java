package org.prd.vertexaidemo.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Slf4j
public class WeatherService implements Function<WeatherService.Request,WeatherService.Response> {

    private final RestClient restClient;
    private final WeatherConfigProperties weatherProps;

    public WeatherService(WeatherConfigProperties weatherProps) {
        this.weatherProps = weatherProps;
        this.restClient = RestClient.create((weatherProps.apiUrl()));
    }

    @Override
    public Response apply(Request request) {
        Response response = restClient.get()
                .uri("/current.json?key={key}&q={q}", weatherProps.apiKey(), request.city)
                .retrieve()
                .body(Response.class);
        log.info("Weather API Response: {}", response);
        return response;
    }


    public record Request(String city) {}
    public record Response(Location location,Current current) {}
    public record Location(String name, String region, String country, Long lat, Long lon,String localtime){}
    public record Current(String temp_c, Condition condition, String wind_mph, String humidity,String last_updated) {}
    public record Condition(String text,String icon){}

}
