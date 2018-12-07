package com.pico;

import com.pico.prometheus.PICOCollector;
import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableConfigurationProperties
@EnablePrometheusEndpoint
public class Application extends WebMvcConfigurerAdapter implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private PICOCollector picoCollector;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        picoCollector.register();
    }
}
