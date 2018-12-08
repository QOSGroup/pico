package com.pico.influxdb;

import org.influxdb.dto.Point;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class PICOMetric {

    private static long DENOMINATOR = 1000000;

    private long       time;
    private String     contract       = "";
    private String     name           = "";
    private BigInteger blockNumber;
    private BigInteger cw             = BigInteger.ZERO;
    private BigInteger teamFound      = BigInteger.ZERO;
    private BigInteger price          = BigInteger.ZERO;
    private BigInteger totalTeamFound = BigInteger.ZERO;
    private BigInteger totalReserve   = BigInteger.ZERO;
    private BigInteger totalSupply    = BigInteger.ZERO;

    public void setTime(long time) {
        this.time = time;
    }

    public void setBlockNumber(BigInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    public void setCw(BigInteger cw) {
        this.cw = cw;
    }

    public void setTotalTeamFound(BigInteger totalTeamFound) {
        this.totalTeamFound = totalTeamFound;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalSupply(BigInteger totalSupply) {
        this.totalSupply = totalSupply;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public void setTeamFound(BigInteger teamFound) {
        this.teamFound = teamFound;
    }

    public void setTotalReserve(BigInteger totalReserve) {
        this.totalReserve = totalReserve;
    }

    public Point point() {
        return Point.measurement("pico")
                    .time(time, TimeUnit.SECONDS)
                    .tag("name", name)
                    .tag("contract", contract)
                    .addField("block_number", blockNumber.longValue())
                    .addField("cw", cw.doubleValue() / DENOMINATOR)
                    .addField("team_found", teamFound.longValue())
                    .addField("total_supply", totalSupply.longValue())
                    .addField("total_team_found", totalTeamFound.longValue())
                    .addField("total_reserve", totalReserve.longValue())
                    .addField("price", price.doubleValue() / DENOMINATOR)
                    .build();
    }
}
