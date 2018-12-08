package com.pico;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EnablePrometheusEndpoint
public class Application{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
