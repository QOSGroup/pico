package com.pico.influxdb;

import com.pico.web3j.PICOToken;
import io.reactivex.disposables.Disposable;
import org.influxdb.InfluxDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.exceptions.ContractCallException;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.List;

@Component
public class Fetch {

    private Logger log = LoggerFactory.getLogger(Fetch.class);

    private File cacheFile = new File(System.getProperty("user.home") + File.separator + ".pico.index");

    private BigInteger startBlock = BigInteger.valueOf(4580000);

    @Autowired
    private List<PICOToken> picoTokenList;

    @Autowired
    private InfluxDB influxDB;

    @Autowired
    private Web3j web3j;

    @PostConstruct
    public void init() {
        if (cacheFile.exists()) {
            try {
                BufferedReader bw = new BufferedReader(new FileReader(cacheFile));
                String offset = bw.readLine();
                startBlock = new BigInteger(offset);
            } catch (Exception e) {
                log.error("", e);
            }
        }

        if(picoTokenList.size()>0) {
            filterTx();
        }
    }

    public void saveIndex(BigInteger index) {
        try {
            FileWriter fileWriter = new FileWriter(cacheFile);
            fileWriter.write(index.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void fetchState(EthBlock.Block block) {
        for (PICOToken picoToken : picoTokenList) {
            try {
                picoToken.setDefaultBlockParameter(DefaultBlockParameter.valueOf(block.getNumber()));
                PICOMetric picoMetric = new PICOMetric();
                picoMetric.setTime(block.getTimestamp().longValue());
                picoMetric.setBlockNumber(block.getNumber());
                picoMetric.setContract(picoToken.getContractAddress());
                picoMetric.setName(picoToken.name().send());
                picoMetric.setCw(picoToken._cweight().send());
                picoMetric.setTeamFound(picoToken.teamFound().send());
                picoMetric.setTotalReserve(picoToken.totalReserve().send());
                picoMetric.setTotalSupply(picoToken.totalSupply().send());
                picoMetric.setTotalTeamFound(picoToken.totalTeamFound().send());
                picoMetric.setPrice(picoToken.getCurrentPrice().send());
                picoToken.setDefaultBlockParameter(DefaultBlockParameterName.LATEST);
                influxDB.write(picoMetric.point());
            } catch (Exception e1){
                log.error("",e1);
            }
        }
    }

    public void filterTx() {
        Disposable disposable = web3j.replayPastAndFutureBlocksFlowable(DefaultBlockParameter.valueOf(startBlock),
                                                               true).subscribe(ethBlock -> {
            parseBlock(ethBlock.getBlock());
            saveIndex(ethBlock.getBlock().getNumber());
        });

//        web3j.blockFlowable(true).subscribe(ethBlock -> {
//            parseBlock(ethBlock.getBlock());
//            if (disposable.isDisposed()) {
//                saveIndex(ethBlock.getBlock().getNumber());
//            }
//        });
    }

    public void parseBlock(EthBlock.Block block) {
        log.info("block:{},{},{},{}", block.getNumber(), block.getHash(), block.getTimestamp(),
                 block.getTransactionsRoot().length());
        fetchState(block);
        for (EthBlock.TransactionResult txr : block.getTransactions()) {
            try {
                EthBlock.TransactionObject tx = (EthBlock.TransactionObject) txr.get();
                for (PICOToken picoToken : picoTokenList) {
                    if (picoToken.getContractAddress().equalsIgnoreCase(tx.getTo())) {
                        PICOTx picoTx = new PICOTx();
                        picoTx.setTime(block.getTimestamp().longValue());
                        picoTx.setContract(picoToken.getContractAddress());
                        picoTx.setName(picoToken.name().send());
                        picoTx.setBlockNumber(tx.getBlockNumber());
                        picoTx.setHash(tx.getHash());
                        picoTx.setFrom(tx.getFrom().substring(0, 8));
                        picoTx.setValue(tx.getValue());
                        picoTx.setTo(tx.getTo().substring(0, 8));
                        TransactionReceipt receipt = web3j.ethGetTransactionReceipt(
                                tx.getHash()).send().getResult();
                        picoTx.setStatus(receipt.getStatus());
                        for(PICOToken.ReservedEventResponse reserved:picoToken.getReservedEvents(receipt)){
                            picoTx.setMethod("Buy");
                            picoTx.setMethodNumber(10);
                            picoTx.setValue(reserved._amount);
                            for(PICOToken.IssuedEventResponse issue:picoToken.getIssuedEvents(receipt)){
                                picoTx.setToken(issue._amount);
                            }
                        }

                        for(PICOToken.RemovedEventResponse removed:picoToken.getRemovedEvents(receipt)){
                            picoTx.setMethod("Sell");
                            picoTx.setMethodNumber(20);
                            picoTx.setValue(removed._amount);
                            for(PICOToken.BurnedEventResponse burned:picoToken.getBurnedEvents(receipt)){
                                picoTx.setToken(burned._amount);
                            }
                        }
                        influxDB.write(picoTx.point());
                        continue;
                    }
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }

    }

}
