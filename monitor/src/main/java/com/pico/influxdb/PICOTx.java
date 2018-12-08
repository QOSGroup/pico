package com.pico.influxdb;

import org.influxdb.dto.Point;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class PICOTx {
    private long time;

    private String name;

    private String contract;

    private String method;

    private String status;

    private BigInteger blockNumber;

    private String hash;

    private String from;

    private String to;

    private BigInteger value;

    public void setTime(long time) {
        this.time = time;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBlockNumber(BigInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public Point point() {
        return Point.measurement("tx")
                    .time(time, TimeUnit.SECONDS)
                    .tag("name", name)
                    .tag("contract", contract)
//                    .tag("method", method)
                    .tag("status", status)
                    .addField("block_number", blockNumber)
                    .addField("hash", hash)
                    .addField("from", from)
                    .addField("to", to)
                    .addField("value", value.longValue())
                    .build();
    }
}
