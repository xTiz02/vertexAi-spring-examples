package org.prd.vertexaidemo.function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Configuration
public class WeatherFunction {

    private final WeatherConfigProperties weatherProps;

    public WeatherFunction(WeatherConfigProperties weatherProps) {
        this.weatherProps = weatherProps;
    }


    @Bean
    @Description("Get the current weather conditions for the given city.")
    public Function<WeatherService.Request,WeatherService.Response> currentWeatherFunction() {
        return new WeatherService(weatherProps);
    }
}
