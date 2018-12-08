# PICO
Practical ICO - a customized and transparent ICO procedure

# Inspiration
ICOs sometimes play a questioned role in cryptoeconomic, for whitepapers are not easy to understand by ordinary investors, both technically and economically, and once done, technically there is no restrain for the already profited team. As a result of some bad examples, both investors and honest teams were harmed.

Bancor protocol is an asynchronous price discovery mechanism, also offers a good practice for creating tokens based on one or more connector token(s), the later provides reserved value for the new tokens, and for a given connector weight, the curve of a created token’s price could be predicable and transparent to end users.

Our idea is that the ICO should be a procedure, which takes a period of time, and Bancor’s connector weight factor could be involved in all along the procedure of initialization of the new token, and other factors should also be considered, such as the necessary project funding, the interaction between the developing team and investors, the adjustment of TF function/parameters(by governance voting of investors, for example) during ICO procedure according to the progress of development. The TF settled, the potential profit of the team would be visible to investors. We are aiming to construct a bridge of trust.

# What it does
We add a TF(aka Team Funding) factor to the original formula of bancor. TF should be a function defined by the team, may take parameters like funding time, token issued/destroyed, connector token paid/returned etc. 

This TF factor is added on behalf of the continuity of the team’s funding, for their reasonable need or profit without avarice. It will be designed by the team out of its own consideration, and once the extended contract is deployed, it could stimulate and demonstrate the predicting curves of both buying and selling, give investors a clear idea how much contribution they will make to the project and how much they could profit from an early involvement, help to make decisions.

# How we BUIDL it
## The math work
Our curve of supply-price should be coherent with original bancor protocol, at the same time it makes sure the team get some profit from the difference between buyings and sellings, for in most exchanging cases, buying price would be higher than selling price. On buying smart tokens, TF contributes as a part of connector token balance while on selling them, it doesn’t.

On buying, the formula will look like:

![buying]( https://github.com/QOSGroup/pico/blob/master/statics/buying.png?raw=true)

On selling, the formula remains the same as original:
![selling]( https://github.com/QOSGroup/pico/blob/master/statics/selling.png?raw=true)

In-between buying and then selling the same number of smart tokens on the same total supply, the team funding collects:

![TF_inc]( https://github.com/QOSGroup/pico/blob/master/statics/TF_inc.png?raw=true)

## Working based on bancor protocol
We extended some of bancor's code for recalculation and implements getTF() function in solidity with varies CWs and getTFs

## Testing and proving
We deployed the contract on Ropsten and send test buying/selling txs, collected and displayed data, utilizing web3js, influxdb and Grafana.

We tested 3 simple modules:
* TF = 0 and CW = 0.5
* TF is a constant
* TF is a percentage of connector tokens paid

Check out the result at http://18.144.37.250/d/Z5Kv-HPiz/pico?orgId=1&from=1544269748605&to=1544276767808&var-name=IToken

The results produced on testnet is coherent with our simulator in javascript.

## Enable user to issue ERC20 tokens with all other ERC20 tokens
Thanks to the warm help from Kyber!

# Challenges we ran into
Doing the math, and doing the right math in the code.

# Accomplishments that We are proud of
We are quite happy with the result

# What we learned
Encountered all different thoughts on tokens trading.

# What's next
We’d like to continue this work to make it really practical by adjusting and more testing, and introduce more interesting and useful features such as governance of investors’ voting.

# Build with
Solidity, Kyber api, java, javascript, bash