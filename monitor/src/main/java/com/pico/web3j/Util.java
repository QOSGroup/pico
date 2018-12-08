package com.pico.web3j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;

public class Util {
    private static Logger log= LoggerFactory.getLogger(Util.class);

    public String     RPC_URL        = "http://18.144.37.250:8333/";
    public String     baseAddress    = "0xecfdbe9a0d897100cd386e785cd2bacc00c36736";
    public String     basePassword   = "12345678";
    public BigInteger unlockDuration = BigInteger.valueOf(60L);
    public String contractAddress= "0x34c01eebfb06b5f191791a4eae814c11fb886ff1";

    public Web3j                    web3j                        = Web3j.build(new HttpService(RPC_URL));
    public Admin                    admin                        = Admin.build(new HttpService(RPC_URL));
    public ClientTransactionManager baseClientTransactionManager = new ClientTransactionManager(web3j,
                                                                                                baseAddress);

    public boolean deploy() {
        ContractGasProvider contractGasProvider = new StaticGasProvider(ManagedTransaction.GAS_PRICE,BigInteger.valueOf(6_300_000));

        if (!unlock(baseAddress, basePassword)) {
            return false;
        }

        try {
            PICOToken contract = PICOToken.deploy(
                    web3j,
                    baseClientTransactionManager,
                    contractGasProvider,
                    new BigInteger("500"),
                    "pico3",
                    "pico3",
                    new BigInteger("4"),
                    new BigInteger("1000"),
                    new BigInteger("500000"),
                    new BigInteger("20")
            ).send();

            contract.getTransactionReceipt().get().getBlockNumber();
            contract.getTransactionReceipt().get().getTransactionHash();
            log.info(contract.getContractAddress());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean invest() {
        ContractGasProvider contractGasProvider = new StaticGasProvider(BigInteger.valueOf(1000),BigInteger.valueOf(6_300_000));

        if (!unlock(baseAddress, basePassword)) {
            return false;
        }

        try {
            PICOToken contract = PICOToken.load(contractAddress, web3j, baseClientTransactionManager,
                                                new DefaultGasProvider());
            log.info("total_reserve:{}",contract.totalReserve().send());
            log.info("balance:{}",contract.balanceOf(baseAddress).send());

            TransactionReceipt receipt=contract.invest(contractAddress, BigInteger.ZERO, BigInteger.TEN).send();
            log.info("block:{}",receipt.getBlockNumber());
            log.info("tx:{}",receipt.getTransactionHash());
            log.info("balance:{}",contract.balanceOf(baseAddress).send());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean unlock(String address, String password) {
        try {
            admin.personalUnlockAccount(address, password, unlockDuration).send();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args){
//        new Util().deploy();
        new Util().invest();
    }

}
