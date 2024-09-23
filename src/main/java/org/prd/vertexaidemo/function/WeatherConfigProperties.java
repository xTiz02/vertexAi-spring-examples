package org.prd.vertexaidemo.function;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "weather")//hace que se busque en el application.properties las propiedades con el prefijo weather
public record WeatherConfigProperties(String apiKey, String apiUrl) {
}
