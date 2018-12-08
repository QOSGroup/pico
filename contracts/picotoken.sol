pragma solidity 0.4.18;

import "./utils.sol";
import "./KyberNetworkProxyInterface.sol";

/**
 * @title SafeMath
 * @dev Math operations with safety checks that throw on error
 */
library SafeMath {

  /**
  * @dev Multiplies two numbers, throws on overflow.
  */
  function mul(uint256 a, uint256 b) internal pure returns (uint256 c) {
    // Gas optimization: this is cheaper than asserting 'a' not being zero, but the
    // benefit is lost if 'b' is also tested.
    // See: https://github.com/OpenZeppelin/openzeppelin-solidity/pull/522
    if (a == 0) {
      return 0;
    }

    c = a * b;
    assert(c / a == b);
    return c;
  }

  /**
  * @dev Integer division of two numbers, truncating the quotient.
  */
  function div(uint256 a, uint256 b) internal pure returns (uint256) {
    // assert(b > 0); // Solidity automatically throws when dividing by 0
    // uint256 c = a / b;
    // assert(a == b * c + a % b); // There is no case in which this doesn't hold
    return a / b;
  }

  /**
  * @dev Subtracts two numbers, throws on overflow (i.e. if subtrahend is greater than minuend).
  */
  function sub(uint256 a, uint256 b) internal pure returns (uint256) {
    assert(b <= a);
    return a - b;
  }

  /**
  * @dev Adds two numbers, throws on overflow.
  */
  function add(uint256 a, uint256 b) internal pure returns (uint256 c) {
    c = a + b;
    assert(c >= a);
    return c;
  }
}

/**
 * @title ERC20Basic
 * @dev Simpler version of ERC20 interface
 * See https://github.com/ethereum/EIPs/issues/179
 */
contract ERC20Basic {
  function totalSupply() public view returns (uint256);
  function balanceOf(address who) public view returns (uint256);
  function transfer(address to, uint256 value) public returns (bool);
  event Transfer(address indexed from, address indexed to, uint256 value);
}

/**
 * @title Basic token
 * @dev Basic version of StandardToken, with no allowances.
 */
contract BasicToken is ERC20Basic {
  using SafeMath for uint256;

  mapping(address => uint256) balances;

  uint256 totalSupply_;

  /**
  * @dev Total number of tokens in existence
  */
  function totalSupply() public view returns (uint256) {
    return totalSupply_;
  }

  /**
  * @dev Transfer token for a specified address
  * @param _to The address to transfer to.
  * @param _value The amount to be transferred.
  */
  function transfer(address _to, uint256 _value) public returns (bool) {
    require(_to != address(0));
    require(_value <= balances[msg.sender]);

    balances[msg.sender] = balances[msg.sender].sub(_value);
    balances[_to] = balances[_to].add(_value);
    Transfer(msg.sender, _to, _value);
    return true;
  }

  /**
  * @dev Gets the balance of the specified address.
  * @param _owner The address to query the the balance of.
  * @return An uint256 representing the amount owned by the passed address.
  */
  function balanceOf(address _owner) public view returns (uint256) {
    return balances[_owner];
  }

}

/**
 * @title ERC20 interface
 * @dev see https://github.com/ethereum/EIPs/issues/20
 */
contract ERC20Token is ERC20Basic {
  function allowance(address owner, address spender)
    public view returns (uint256);

  function transferFrom(address from, address to, uint256 value)
    public returns (bool);

  function approve(address spender, uint256 value) public returns (bool);
  event Approval(
    address indexed owner,
    address indexed spender,
    uint256 value
  );
}

/**
 * @title Standard ERC20 token
 *
 * @dev Implementation of the basic standard token.
 * https://github.com/ethereum/EIPs/issues/20
 * Based on code by FirstBlood: https://github.com/Firstbloodio/token/blob/master/smart_contract/FirstBloodToken.sol
 */
contract StandardToken is ERC20Token, BasicToken {

  mapping (address => mapping (address => uint256)) internal allowed;


  /**
   * @dev Transfer tokens from one address to another
   * @param _from address The address which you want to send tokens from
   * @param _to address The address which you want to transfer to
   * @param _value uint256 the amount of tokens to be transferred
   */
  function transferFrom(
    address _from,
    address _to,
    uint256 _value
  )
    public
    returns (bool)
  {
    require(_to != address(0));
    require(_value <= balances[_from]);
    require(_value <= allowed[_from][msg.sender]);

    balances[_from] = balances[_from].sub(_value);
    balances[_to] = balances[_to].add(_value);
    allowed[_from][msg.sender] = allowed[_from][msg.sender].sub(_value);
    Transfer(_from, _to, _value);
    return true;
  }

  event Approval(
    address indexed owner,
    address indexed spender,
    uint256 value
  );
  /**
   * @dev Approve the passed address to spend the specified amount of tokens on behalf of msg.sender.
   * Beware that changing an allowance with this method brings the risk that someone may use both the old
   * and the new allowance by unfortunate transaction ordering. One possible solution to mitigate this
   * race condition is to first reduce the spender's allowance to 0 and set the desired value afterwards:
   * https://github.com/ethereum/EIPs/issues/20#issuecomment-263524729
   * @param _spender The address which will spend the funds.
   * @param _value The amount of tokens to be spent.
   */
  function approve(address _spender, uint256 _value) public returns (bool) {
    require((_value == 0) || (allowed[msg.sender][_spender] == 0));
    allowed[msg.sender][_spender] = _value;
    Approval(msg.sender, _spender, _value);
    return true;
  }

  /**
   * @dev Function to check the amount of tokens that an owner allowed to a spender.
   * @param _owner address The address which owns the funds.
   * @param _spender address The address which will spend the funds.
   * @return A uint256 specifying the amount of tokens still available for the spender.
   */
  function allowance(
    address _owner,
    address _spender
   )
    public
    view
    returns (uint256)
  {
    return allowed[_owner][_spender];
  }

  /**
   * @dev Increase the amount of tokens that an owner allowed to a spender.
   * approve should be called when allowed[_spender] == 0. To increment
   * allowed value is better to use this function to avoid 2 calls (and wait until
   * the first transaction is mined)
   * From MonolithDAO Token.sol
   * @param _spender The address which will spend the funds.
   * @param _addedValue The amount of tokens to increase the allowance by.
   */
  function increaseApproval(
    address _spender,
    uint256 _addedValue
  )
    public
    returns (bool)
  {
    allowed[msg.sender][_spender] = (
      allowed[msg.sender][_spender].add(_addedValue));
    Approval(msg.sender, _spender, allowed[msg.sender][_spender]);
    return true;
  }

  /**
   * @dev Decrease the amount of tokens that an owner allowed to a spender.
   * approve should be called when allowed[_spender] == 0. To decrement
   * allowed value is better to use this function to avoid 2 calls (and wait until
   * the first transaction is mined)
   * From MonolithDAO Token.sol
   * @param _spender The address which will spend the funds.
   * @param _subtractedValue The amount of tokens to decrease the allowance by.
   */
  function decreaseApproval(
    address _spender,
    uint256 _subtractedValue
  )
    public
    returns (bool)
  {
    uint256 oldValue = allowed[msg.sender][_spender];
    if (_subtractedValue > oldValue) {
      allowed[msg.sender][_spender] = 0;
    } else {
      allowed[msg.sender][_spender] = oldValue.sub(_subtractedValue);
    }
    Approval(msg.sender, _spender, allowed[msg.sender][_spender]);
    return true;
  }

}

contract PICOToken is StandardToken, Utils {
    address public OWNER;
    address public KyberNetworkProxy_;
    address public EtherAddress;
    string public name;
    string public symbol;
    uint8 public decimals;
    uint32 public _cweight;
    uint32 public _denominator;
    uint256 public teamFound;
    uint256 public totalReserve;
    uint256 public totalTeamFound;

    event Issued(uint256 _amount);
    event Burned(uint256 _amount);

    event Reserved(uint256 _amount);
    event Removed(uint256 _amount);

    function PICOToken(string _name, string _symbol, uint8 _decimals, 
        uint256 _total, uint32 _cw, uint256 _tf) public payable {
        OWNER = msg.sender;
        name = _name;
        symbol = _symbol;
        decimals = _decimals;

        _denominator = 1000000;
        require(_cw>0 && _cw<_denominator);
        require(_total > 0);
        require(msg.value > 0);

        _cweight = _cw;
        teamFound = _tf;
        totalReserve = msg.value;
        totalTeamFound = 0;

        uint256 decimalValue = 10 ** uint256(decimals);
        totalSupply_ = SafeMath.mul(_total, decimalValue);

        KyberNetworkProxy_ = address(0x818E6FECD516Ecc3849DAf6845e3EC868087B755);
        EtherAddress = ERC20(0x00eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee);
    }

    function getTF() public view returns (uint256){
        return teamFound;
    }


    function getSupply(uint256 _r) internal view returns (uint256) {
        require(totalSupply_> 0 && totalReserve> 0);

        if(_r == 0)
            return 0;

        uint256 result;
        uint8 precision;
        uint256 _nextReserve = SafeMath.add(SafeMath.add(_r, totalReserve), getTF());
        uint256 _allReserve = SafeMath.add(totalReserve, getTF());
        (result, precision) = power(_nextReserve, _allReserve, _cweight, _denominator);
        uint256 temp = SafeMath.mul(totalSupply_, result) >> precision;
        return temp - totalSupply_;
    }

    function getReserve(uint256 _s, bool _test) internal view returns (uint256){
        require(_s<= totalSupply_);
        if (!_test)
            require(_s <= balances[msg.sender]);
        if(_s == 0)
            return 0;


        // selling the entire supply
        if (_s == totalSupply_)
            return totalReserve;

        uint256 result;
        uint8 precision;
        uint256 _spl = totalSupply_ - _s;
        (result, precision) = power(totalSupply_, _spl, _denominator, _cweight);
        uint256 temp1 = SafeMath.mul(totalReserve, result);
        uint256 temp2 = totalReserve << precision;
        return (temp1 - temp2) / result;
    }

    function getCurrentPrice() public view returns (uint256) {
        uint256 _t1 = SafeMath.mul(totalReserve, _denominator);
        uint256 _t2 = SafeMath.mul(totalSupply_, _cweight);
        _t1 = SafeMath.mul(_t1, 10000); //decimals
        return SafeMath.div(_t1, _t2);
    }

    function swapToEther(ERC20 _token, uint256 _amount) internal returns (uint256) {
        uint256 minRate;

        KyberNetworkProxyInterface _kyberNetworkProxy = KyberNetworkProxyInterface(KyberNetworkProxy_);
        (, minRate) = _kyberNetworkProxy.getExpectedRate(_token, ERC20(EtherAddress), _amount);

        // Check that the token transferFrom has succeeded
        require(_token.transferFrom(msg.sender, this, _amount));

        // Mitigate ERC20 Approve front-running attack, by initially setting
        // allowance to 0
        require(_token.approve(_kyberNetworkProxy, 0));

        // Approve tokens so network can take them during the swap
        _token.approve(address(_kyberNetworkProxy), _amount);
        return _kyberNetworkProxy.swapTokenToEther(_token, _amount, minRate);
        // Send received tokens to destination address
        //require(EtherAddress.transfer(destAddress, destAmount));
    }

    /* duidu protocal ?
        investors invest their token to this token, and get this tokens back.
        record this number in _balance, get the number by balanceOf function
    */
    function invest(ERC20 _token, uint256 _amount) public payable { 
        require(address(_token) != address(0));
        uint256 _r = msg.value;
        if (msg.value == 0) {
            //This should be called outside before this function.
            //require(_token.approve(address(this), _amount));
            require(_amount > 0);

            _r = swapToEther(_token, _amount);

        } else {

            require( _amount == _r);
        }


        uint256 _s = getSupply(_r); 
        uint256 _rr = getReserve(_s, true); //real Reserve value
        uint256 _dr = SafeMath.sub(_r, _rr);
        totalReserve = SafeMath.add(totalReserve, _rr);
        totalSupply_ = SafeMath.add(totalSupply_, _s);
        totalTeamFound = SafeMath.add(totalTeamFound, _dr);
        //SafeMath.mul(totalReserve, _denominator).div(_cweight);

        balances[msg.sender] = SafeMath.add(balances[msg.sender], _s);

        OWNER.transfer(_dr);

        Issued(_s);
        Reserved(_r);
        Transfer(address(this), msg.sender, _s);
        Transfer(msg.sender, address(this), _r);
        Transfer(address(this), OWNER, _dr);
    }

    /**
        return invest count
    */
    function getInvest(ERC20 _token, uint256 _amount) public view returns (uint256) {
        require(_amount != 0);

        uint256 _r = _amount;
        if (address(_token) != address(0)) {
            uint256 minRate;

            KyberNetworkProxyInterface _kyberNetworkProxy = KyberNetworkProxyInterface(KyberNetworkProxy_);
            (, minRate) = _kyberNetworkProxy.getExpectedRate(_token, ERC20(EtherAddress), _amount);

            _r = minRate * _amount;

        }

        return getSupply(_r);
    }

    function swapEtherToToken (ERC20 _token, address destAddress, uint256 value) internal {

        uint minRate;
        KyberNetworkProxyInterface _kyberNetworkProxy = KyberNetworkProxyInterface(KyberNetworkProxy_);
        (, minRate) = _kyberNetworkProxy.getExpectedRate(ERC20(EtherAddress), _token, value);

        //will send back tokens to this contract's address
        uint destAmount = _kyberNetworkProxy.swapEtherToToken.value(value)(_token, minRate);

        //send received tokens to destination address
        require(_token.transfer(destAddress, destAmount));
    }


    /**
        if investors want to withdraw their invests, they can call withdraw function to withdraw their tokens back.
        this token will be burned.
    */
    function withdraw(ERC20 _token, uint256 _amount) public { 
        require(_amount>0 && _amount <= balances[msg.sender]);

        uint256 _s = _amount;
        uint256 _r = getReserve(_s, false); 
        totalSupply_ = SafeMath.sub(totalSupply_, _s);
        totalReserve = SafeMath.sub(totalReserve, _r);

        // withdraw ether into _token type
        if (address(_token) != address(0)) {
            swapEtherToToken(_token, msg.sender, _r);
        } else {

            msg.sender.transfer(_r);
        }

        balances[msg.sender] = SafeMath.sub(balances[msg.sender], _s);

        Transfer(address(this), msg.sender, _r);
        Burned(_s);
        Removed(_r);
    }

    /**
        return withdraw count
    */
    function getWithdraw(ERC20 _token, uint256 _amount) public view returns (uint256){
        require(_amount != 0);
        uint256 _s = _amount;
        uint256 _r = getReserve(_s, true); 

        if (address(_token) != address(0)) {
            uint256 minRate;

            KyberNetworkProxyInterface _kyberNetworkProxy = KyberNetworkProxyInterface(KyberNetworkProxy_);
            (, minRate) = _kyberNetworkProxy.getExpectedRate(ERC20(EtherAddress), _token, _r);

            _r = minRate * _r;

        }
        return _r;
    }
}
