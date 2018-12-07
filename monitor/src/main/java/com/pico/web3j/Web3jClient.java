package com.pico.web3j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

@Configuration
public class Web3jClient {

    private static final Logger log = LoggerFactory.getLogger(Web3jClient.class);
    private              Web3j  web3j;

    @Bean
    public Web3j web3jClientBean() throws Exception {
        if (web3j == null) {
            web3j = Web3j.build(new HttpService("http://18.144.37.250:8333/"));
            log.info(
                    "Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        }
        return web3j;
    }

    @Bean
    public Admin AdminClientBean() throws Exception {
        Admin admin = Admin.build(new HttpService("http://18.144.37.250:8333/"));
        log.info(
                "Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        return admin;
    }

    @Bean
    public TransactionManager transactionManager() throws Exception {
        return new ReadonlyTransactionManager(web3jClientBean(), "0x57c1282128F7d9dE4154dC4f6dc52C5aC6dD3fea");
    }

    @Bean
    public PICOToken loadPICToken() throws Exception {
        return PICOToken.load("0x57c1282128F7d9dE4154dC4f6dc52C5aC6dD3fea", web3jClientBean(), transactionManager(),
                              new DefaultGasProvider());
    }

}
