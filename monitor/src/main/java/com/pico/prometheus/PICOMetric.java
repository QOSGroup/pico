package com.pico.prometheus;

import java.math.BigInteger;

public class PICOMetric {
    private String name="";
    private BigInteger totalSupply=BigInteger.ZERO;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(BigInteger totalSupply) {
        this.totalSupply = totalSupply;
    }
}
