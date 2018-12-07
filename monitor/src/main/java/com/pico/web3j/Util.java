package com.pico.web3j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;

public class Util {
    private static Logger log= LoggerFactory.getLogger(Util.class);

    public String     RPC_URL        = "http://18.144.37.250:8333/";
    public String     baseAddress    = "0xecfdbe9a0d897100cd386e785cd2bacc00c36736";
    public String     basePassword   = "12345678";
    public BigInteger unlockDuration = BigInteger.valueOf(60L);

    public Web3j                    web3j                        = Web3j.build(new HttpService(RPC_URL));
    public Admin                    admin                        = Admin.build(new HttpService(RPC_URL));
    public ClientTransactionManager baseClientTransactionManager = new ClientTransactionManager(web3j,
                                                                                                baseAddress);

    public boolean deploy() {
        ContractGasProvider contractGasProvider = new DefaultGasProvider();

        if (!unlock(baseAddress, basePassword)) {
            return false;
        }

        try {
            PICOToken contract = PICOToken.deploy(
                    web3j,
                    baseClientTransactionManager,
                    contractGasProvider,
                    "pci",
                    "pci",
                    new BigInteger("4")
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

    private boolean unlock(String address, String password) {
        try {
            admin.personalUnlockAccount(address, password, unlockDuration).send();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args){
        new Util().deploy();
    }

}
