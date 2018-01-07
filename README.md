[![Build Status](https://travis-ci.org/proxa/IRCbutt.svg?branch=master)](https://travis-ci.org/proxa/IRCbutt)

# IRCbutt
A Java IRC bot inspired by buttbot, with various other functionality.

Original ideas, and some borrowed from phrik from #archlinux on freenode

![Real photograph of buttbutt](buttbot.gif "Real photographs of buttbutt")

## Download

IRCbutt is built using OpenJDK 1.8

You can test it here: http://alureon.net/irc/

(Or you can just join #afterlunch on freenode)

Download builds here: http://alureon.net/ircbutt

## Running

Run with `java -jar IRCbutt*.jar`

## Usage:
#### Basic Usage:
Most commands start with `!` followed by the command name, then arguments.

Fact requests start with either a `~` or `!` followed immediately by the fact name.

You don't include the brackets `<>`

#### Command Substitution:

Most commands support command substitution.  An example of this would be `!echo $(rot $(fact))`

The result of this would be a rot13 encrypted, random fact.

For extra mature fun, try `!butt $(fact)`

#### Variables:

The variable `$USER` will be replaced with the user making the request. 

Try `!echo $USER`

When used in facts, the variable `$ME` will cause the bot to do a `/me` command in IRC.

The variables `$1`, `$2`, `$3`, `$4`, etc can be used in facts as the corresponding arguments to a fact.
For example, the fact "Hello $1!" when called with `~hello test` will print "Hello test!".

### Basic Functionality

`!8 <question>`         -  Ask the Magic 8 Ball a question

`!a <query>`            -  Search Amazon for an item.

`!butt <query>`         -  Replace random words in the text with 'butt' (for mature audiences only)

`!check <query>`        -  Get a random response on whether or not something passes or fails (or rarely, something else)

`!coin <query>`         -  Get a random heads or tails response (or rarely, another surprise)

`!define <query>`       -  Get the definition of a word from Merriam Webster

`!dice`                 -  Roll a dice to land on a random user in the room that will either WIN or LOSE.

`!echo <query>`         -  Echo something (commands can be echoed with $(command))

`!g <query>`            -  Google something

`!gi <query>`           -  Google Image Search

`!give <user> <thing>`  -  Give something to someone else

`!more`                 -  Get more results for facts, quotes, or search results

`!rot <query>`          -  Get the rot13 equivalent of `<query>`.  Alias: !rot

`!s/search/replace/`    -  This works just like Vim's search and replace feature.

`!uptime`               -  Print the current uptime of the bot

`!version`              -  Get the current bot version

`!wr`                   -  Wake the room!  Ping everbody in the room.

`!yt <query>`           -  Search YouTube for `<query>`

### Cryptocurrency Functionality

The bot can get information for many cryptocurrencies such as the current market value, 24 change, and market cap from
the public CoinMarketCap API. More can easily be added in the future to CryptoCurrencyCommand.

This is done by doing the following:

`!<ticker symbol>`    -  Get all the available information for the specified cryptocurrency.

You can also perform mathematical operations on cryptocurrency values using !eval and such.  This is done by appending the
letter 'v' (for value) to the ticker symbol, as shown below.

`!<ticker symbol>v`   -  Get only the current market value as an unrounded double.

Currently supported currencies are as follows:
"btc", "ltc", "eth", "vtc", "xrp", "bch", "dash", "iota", "wtc", "ada", "xem", "btg",, "xmr", "eos", "xlm", "zec", 
"usdt", "steem", "doge", "bnb", "gnt", "etc", "neo", "ppt", "bcc", "qtum", "waves", "trx", "xvg", "icx", "poe"

`!top`                -  Show the top 10 cryptocurrencies on CoinMarketCap and information about them.

`!top 10 20`          -  Show the top cryptocurrencies on CoinMarketCap from rage 10 to 20.

### Math Functionality

`!sqrt <number>`      -  Get the square root of `<number>`

`!pow <num1> <num2>`  -  Get the result of num1^num2

`!eval <expression>`  -  Use Jeval to evaluate an expression. ex: !eval sin(0)

Eval can also be used with other commands.  Ex: `!eval $(ltcv) * 10` to see the value of 10 Litecoins.

### Fact Functionality

`~<string>`                   -  Any string prefixed with a tilde finds the corresponding fact

`!<string>`                   -  Any string prefixed with an exclamation mark (that has a fact) finds the corresponding fact.

`!fact`                       -  Retrieve a random factoid from the database

`!learn <factname> <value>`   -  Write a factoid to the database

`!ff <query>`                 -  Search for a fact (supports regular expressions)

`!fi <factnumber>`            -  Get information about a factoid

`!append <factname> <string>` -  Appends `<string>` to a current factoid

`!forget <factname>`          -  Delete a fact from the database (MUST BE CHANNEL OP!)

### Quotegrab Functionality

`!grab <username>`     -  Grab and store the last message from `<user>` in the database

`!rq`                  -  Retrieve a random quote from the database

`!q <username>`        -  Retrieve the last grabbed quote from `<user>`

`!qf <query>`          -  Search for a quote (supports regular expressions)

`!qi <query>`          -  Get information about a quote

`!qsay <quotenum>`     -  Say quote by ID

`!qdel <id>`           -  Delete a quote from the database (MUST BE CHANNEL OP!)

### Karma Functionality

`<randomstring>++`      -  Increase the karma of `<whatever>`

`<randomstring>--`      -  Decrease the karma of `<whatever>`

`!karma <randomstring>` -  Retrieve the current karma of `<whatever>`

## Configuration File
```
   Bot:
       Name: buttbutt  // the bot's IRC nickname
       Nickname: butt  // the name the bot uses to refer to itself in some messages
       Login: buttbutt // the bot's IRC "login" value
       Realname: butt  // the bot's IRC "realname" value
       Password:       // the bot's IRC "password" value
       Message-Delay: 0 // the delay, if any, before sending a message
       No-Verify: false // false if the bot shouldn't learn from unverified users
       Random-Response-Frequency: 100 // chance of butting a message is 1 in this number
   Channels: // a list of channels the bot should join
       - '#afterlunch'
       - '##anotherchannel'
   Server: // standard settings for connecting to IRC servers
       Hostname: 'chat.freenode.net'
       SSL: false // this doesn't work.  don't use it right now.
       Port: 6667
       Auto-Reconnect: true
   SQL: // standard settings for connecting to an SQL database.
       Username: root
       Password: secret
       IP: 127.0.0.1
       Port: 3306
       Database: irc
       Table-Prefix: ircbutt
```

## Extensibility

On startup, the bot registers any command within the command package.

You can make your own commands, simply by implementing the Command interface.

An example of this is the following:

```java
/**
 * Handles the !echo functionality of the bot.
 */
public final class EchoCommand implements Command {

    @Override
    public BotResponse executeCommand(final IRCbutt butt, final GenericMessageEvent event, final String[] cmd) {
        // A BotResponse requires the bot's intention, user (if applicable), and message.
        // In this case, we are simply echoing out the arguments the user passed in.
        return new BotResponse(BotIntention.CHAT, null, StringUtils.getArgs(cmd));
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        // These are the command aliases that call the command.  You can have as many as you'd like.
        return new ArrayList<>(Collections.singletonList("echo"));
    }

    @Override
    public boolean allowsCommandSubstitution() {
        // true if command substitution is performed.  for echo, this makes sense.
        // for something like a math expression, it wouldn't.
        return true;
    }
}
```

## Special Thanks:

- *BullShark* - for ideas, showing me buttbot, and the name 'buttbutt'
- *Klong* - for tons of contributions and several commits
- *ebolahats* - for being a rubber duck and for several commits
- *MandL27* - for breaking the bot and pointing out what's annoying

