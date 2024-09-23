package org.prd.vertexaidemo;

import org.prd.vertexaidemo.function.WeatherConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(WeatherConfigProperties.class)
@SpringBootApplication
public class VertexAiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(VertexAiDemoApplication.class, args);
    }

}
