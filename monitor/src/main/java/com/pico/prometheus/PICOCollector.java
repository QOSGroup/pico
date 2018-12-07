package com.pico.prometheus;

import com.pico.web3j.PICOToken;
import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PICOCollector extends Collector {

    private static final Logger log = LoggerFactory.getLogger(PICOCollector.class);

    private PICOMetric picoMetric=new PICOMetric();

    @Autowired
    private Web3j web3j;

    @Autowired
    private PICOToken picoToken;

    @PostConstruct
    public void init() throws Exception{
        picoMetric.setName(picoToken.name().send());
    }


    @Override
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> mfs = new ArrayList<>();

        GaugeMetricFamily labeledGauge =
                new GaugeMetricFamily("pico", "custom metrics", Collections.singletonList("labelname"));

        labeledGauge.addMetric(Collections.singletonList("total_supply"), picoMetric.getTotalSupply().longValue());

        mfs.add(labeledGauge);
        return mfs;
    }

    @Scheduled(cron = "0/2 * * * * *")
    public void scheduled() {
        try {
            picoMetric.setTotalSupply(picoToken.totalSupply().send());

        } catch (Exception e) {
            log.error("", e);
        }
    }
}
