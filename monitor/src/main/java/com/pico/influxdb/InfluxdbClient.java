package com.pico.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxdbClient {
    private Logger log = LoggerFactory.getLogger(InfluxdbClient.class);

    @Value("${influxdb.url}")
    private String url;

    @Value("${influxdb.database}")
    private String database;

    @Bean
    public InfluxDB influxDB(){
        InfluxDB influxDB = InfluxDBFactory.connect(url, "root", "root");
        influxDB.setDatabase(database);
        return influxDB;
    }
}
