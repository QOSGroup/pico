package com.pico.web3j;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.0.1.
 */
public class PICOToken extends Contract {
    private static final String BINARY = "60806040523480156200001157600080fd5b5060405162000caf38038062000caf833981018060405260608110156200003757600080fd5b8101908080516401000000008111156200005057600080fd5b820160208101848111156200006457600080fd5b81516401000000008111828201871017156200007f57600080fd5b505092919060200180516401000000008111156200009c57600080fd5b82016020810184811115620000b057600080fd5b8151640100000000811182820187101715620000cb57600080fd5b505060209182015185519194509250620000ec91600391908601906200017c565b508151620001029060049060208501906200017c565b506005805460ff191660ff838116919091179182905516600a0a6200013b6401241011008264010000000062000149810262000a291704565b600155506200022192505050565b60008215156200015c5750600062000176565b508181028183828115156200016d57fe5b04146200017657fe5b92915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620001bf57805160ff1916838001178555620001ef565b82800160010185558215620001ef579182015b82811115620001ef578251825591602001919060010190620001d2565b50620001fd92915062000201565b5090565b6200021e91905b80821115620001fd576000815560010162000208565b90565b610a7e80620002316000396000f3fe6080604052600436106100da5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde0381146100df578063095ea7b31461016957806318160ddd146101b657806323b872dd146101dd578063313ce56714610220578063661884631461024b57806370a082311461028457806384127535146102b757806395d89b41146102f0578063a9059cbb14610305578063b9b8c246146102b7578063d73dd6231461033e578063da360541146102b7578063dd62ed3e14610377578063f3fef3a3146102b7575b600080fd5b3480156100eb57600080fd5b506100f46103b2565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561012e578181015183820152602001610116565b50505050905090810190601f16801561015b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561017557600080fd5b506101a26004803603604081101561018c57600080fd5b50600160a060020a038135169060200135610440565b604080519115158252519081900360200190f35b3480156101c257600080fd5b506101cb6104e3565b60408051918252519081900360200190f35b3480156101e957600080fd5b506101a26004803603606081101561020057600080fd5b50600160a060020a038135811691602081013590911690604001356104e9565b34801561022c57600080fd5b50610235610660565b6040805160ff9092168252519081900360200190f35b34801561025757600080fd5b506101a26004803603604081101561026e57600080fd5b50600160a060020a038135169060200135610669565b34801561029057600080fd5b506101cb600480360360208110156102a757600080fd5b5035600160a060020a0316610759565b3480156102c357600080fd5b506101cb600480360360408110156102da57600080fd5b50600160a060020a038135169060200135610774565b3480156102fc57600080fd5b506100f461080a565b34801561031157600080fd5b506101a26004803603604081101561032857600080fd5b50600160a060020a038135169060200135610865565b34801561034a57600080fd5b506101a26004803603604081101561036157600080fd5b50600160a060020a038135169060200135610946565b34801561038357600080fd5b506101cb6004803603604081101561039a57600080fd5b50600160a060020a03813581169160200135166109df565b6003805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156104385780601f1061040d57610100808354040283529160200191610438565b820191906000526020600020905b81548152906001019060200180831161041b57829003601f168201915b505050505081565b60008115806104705750336000908152600260209081526040808320600160a060020a0387168452909152902054155b151561047b57600080fd5b336000818152600260209081526040808320600160a060020a03881680855290835292819020869055805186815290519293927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a35060015b92915050565b60015490565b6000600160a060020a038316151561050057600080fd5b600160a060020a03841660009081526020819052604090205482111561052557600080fd5b600160a060020a038416600090815260026020908152604080832033845290915290205482111561055557600080fd5b600160a060020a03841660009081526020819052604090205461057e908363ffffffff610a0a16565b600160a060020a0380861660009081526020819052604080822093909355908516815220546105b3908363ffffffff610a1c16565b600160a060020a038085166000908152602081815260408083209490945591871681526002825282812033825290915220546105f5908363ffffffff610a0a16565b600160a060020a03808616600081815260026020908152604080832033845282529182902094909455805186815290519287169391927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929181900390910190a35060019392505050565b60055460ff1681565b336000908152600260209081526040808320600160a060020a0386168452909152812054808311156106be57336000908152600260209081526040808320600160a060020a03881684529091528120556106f3565b6106ce818463ffffffff610a0a16565b336000908152600260209081526040808320600160a060020a03891684529091529020555b336000818152600260209081526040808320600160a060020a0389168085529083529281902054815190815290519293927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a35060019392505050565b600160a060020a031660009081526020819052604090205490565b604080517f70a082310000000000000000000000000000000000000000000000000000000081523360048201529051600091600160a060020a038516916370a0823191602480820192602092909190829003018186803b1580156107d757600080fd5b505afa1580156107eb573d6000803e3d6000fd5b505050506040513d602081101561080157600080fd5b50919392505050565b6004805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156104385780601f1061040d57610100808354040283529160200191610438565b6000600160a060020a038316151561087c57600080fd5b3360009081526020819052604090205482111561089857600080fd5b336000908152602081905260409020546108b8908363ffffffff610a0a16565b3360009081526020819052604080822092909255600160a060020a038516815220546108ea908363ffffffff610a1c16565b600160a060020a038416600081815260208181526040918290209390935580518581529051919233927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a350600192915050565b336000908152600260209081526040808320600160a060020a038616845290915281205461097a908363ffffffff610a1c16565b336000818152600260209081526040808320600160a060020a0389168085529083529281902085905580519485525191937f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929081900390910190a350600192915050565b600160a060020a03918216600090815260026020908152604080832093909416825291909152205490565b600082821115610a1657fe5b50900390565b818101828110156104dd57fe5b6000821515610a3a575060006104dd565b50818102818382811515610a4a57fe5b04146104dd57fefea165627a7a72305820b4506f7849a5d924798bde45d820ea634fd16a6ac59539fd5525e1f16b0130240029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_DECREASEAPPROVAL = "decreaseApproval";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_GETINVEST = "getInvest";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_INVEST = "invest";

    public static final String FUNC_INCREASEAPPROVAL = "increaseApproval";

    public static final String FUNC_GETWITHDRAW = "getWithdraw";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected PICOToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PICOToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PICOToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PICOToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String _spender, BigInteger _value) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_spender), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> decreaseApproval(String _spender, BigInteger _subtractedValue) {
        final Function function = new Function(
                FUNC_DECREASEAPPROVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_spender), 
                new org.web3j.abi.datatypes.generated.Uint256(_subtractedValue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getInvest(String _token, BigInteger _amount) {
        final Function function = new Function(FUNC_GETINVEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> invest(String _token, BigInteger _amount) {
        final Function function = new Function(FUNC_INVEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> increaseApproval(String _spender, BigInteger _addedValue) {
        final Function function = new Function(
                FUNC_INCREASEAPPROVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_spender), 
                new org.web3j.abi.datatypes.generated.Uint256(_addedValue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getWithdraw(String _token, BigInteger _amount) {
        final Function function = new Function(FUNC_GETWITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> allowance(String _owner, String _spender) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_spender)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> withdraw(String _token, BigInteger _amount) {
        final Function function = new Function(FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    @Deprecated
    public static PICOToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PICOToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PICOToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PICOToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PICOToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PICOToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PICOToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PICOToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PICOToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _name, String _symbol, BigInteger _decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_symbol), 
                new org.web3j.abi.datatypes.generated.Uint8(_decimals)));
        return deployRemoteCall(PICOToken.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<PICOToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _name, String _symbol, BigInteger _decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_symbol), 
                new org.web3j.abi.datatypes.generated.Uint8(_decimals)));
        return deployRemoteCall(PICOToken.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PICOToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _name, String _symbol, BigInteger _decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_symbol), 
                new org.web3j.abi.datatypes.generated.Uint8(_decimals)));
        return deployRemoteCall(PICOToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PICOToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _name, String _symbol, BigInteger _decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_symbol), 
                new org.web3j.abi.datatypes.generated.Uint8(_decimals)));
        return deployRemoteCall(PICOToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ApprovalEventResponse {
        public Log log;

        public String owner;

        public String spender;

        public BigInteger value;
    }

    public static class TransferEventResponse {
        public Log log;

        public String from;

        public String to;

        public BigInteger value;
    }
}
