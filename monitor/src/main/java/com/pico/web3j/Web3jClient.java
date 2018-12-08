package com.pico.web3j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Web3jClient {

    private static final Logger log = LoggerFactory.getLogger(Web3jClient.class);
    private              Web3j  web3j;

    @Value("${pico.rpc:}")
    private String rpc;

    @Value("${pico.ipc:}")
    private String ipc;

    @Value("#{'${pico.contract.address:}'.split(',')}")
    private List<String> contracts;

    @Bean
    public Web3j web3jClientBean() throws Exception {
        if (web3j == null) {
            if (ipc != null && ipc.length() != 0) {
                web3j = Web3j.build(new UnixIpcService(ipc));
            } else {
                web3j = Web3j.build(new HttpService(rpc));
            }
            log.info(
                    "Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        }
        return web3j;
    }

    @Bean(name = "web3j")
    public Admin AdminClientBean() throws Exception {
        Admin admin;
        if (ipc != null && ipc.length() != 0) {
            admin = Admin.build(new UnixIpcService(ipc));
        } else {
            admin = Admin.build(new HttpService(rpc));
        }
        log.info(
                "Connected to Ethereum client version: " + admin.web3ClientVersion().send().getWeb3ClientVersion());
        return admin;
    }

    @Bean(name = "admin")
    public List<TransactionManager> transactionManager() throws Exception {
        List<TransactionManager> transactionManagers = new ArrayList<TransactionManager>();
        for (String contract : contracts) {
            if(contract!=null&&contract.length()!=0) {
                transactionManagers.add(new ReadonlyTransactionManager(web3jClientBean(), contract));
            }
        }
        return transactionManagers;
    }

    @Bean
    public List<PICOToken> loadPICToken() throws Exception {
        List<PICOToken> picoTokens = new ArrayList<PICOToken>();
        for (TransactionManager transactionManager : transactionManager()) {
            picoTokens.add(PICOToken.load(transactionManager.getFromAddress(), web3jClientBean(), transactionManager,
                                          new DefaultGasProvider()));
        }
        return picoTokens;
    }

}
