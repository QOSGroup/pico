# pico
Practical ICO - Customized and transparent ICO procedure

# Inspiration
ICOs usually play a questioned role in cryptoeconomic, for whitepapers are not easy to understand by ordinary investors, both technically and economically, and once done, technically there is no restrain for the already profited team. As a result of some bad examples, both investors and honest teams were harmed.

Bancor protocol is a asynchronous price discovery mechanism, also offers a good practice for creating tokens based on one or more connector token(s), the later provides reserved value for the new tokens, and for a given connector weight, the curve of a created token’s price could be predicable and transparent to end users.

Our idea is that the ICO should be a procedure, which takes a period of time, and Bancor’s connector weight factor could be involved all along the procedure of initialization of tokens, and other factors could also be considered, once the parameters of the process been settled, the potential profit of the team would be visible to investors. We are aiming to construct a bridge of trust.

# What it does
We add a TF(team funding) factor to the original pricing formula of bancor. TF is a function defined by the team, probably but not necessarily taking parameters like funding time, token issued/destroyed, connector token paid/returned etc.

This TF factor is added for the sake of teams’ continuity, for reasonable need or profit without avarice. It will be designed by the team out of its own consideration, and once the extended bancor contract is deployed, it would demonstrate the predicted curves of both buying and selling, give investors a clear idea how much contribution they will make and how they could profit from the early involving, help to make decisions.

# How we BUIDL it
Our curve of supply-price should be coherent to original bancor protocol, at the same time it makes sure the team get some profit from buyings and sellings, for in most exchanging occasions, buying price would be higher than selling price. On buying, TF contributes as a part of connector token balance while on selling, it doesn’t.

On buying, the formula becomes:

![buying]( https://github.com/QOSGroup/pico/blob/master/statics/buying.png?raw=true)

On selling, the formula doesn’t change:
![selling]( https://github.com/QOSGroup/pico/blob/master/statics/selling.png?raw=true)

In-between, the team funding collects:

![TF_inc]( https://github.com/QOSGroup/pico/blob/master/statics/TF_inc.png?raw=true)

We would demonstrate TF examples, such as:
* TF is a constant
* TF is a percentage of connector tokens paid
* TF is a sectioned function


# Challenges we ran into

# Accomplishments that We are proud of

# What we learned

# What's next

