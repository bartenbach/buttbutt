#IRCbutt
A Java port of buttbot, with various other functionality.

Original ideas, and some borrowed from phrik from #archlinux on freenode

![Real photograph of buttbutt](buttbot.gif "Real photographs of buttbutt")

##Download
You can test it here: http://alureon.net/irc/

(Or conversely, join #afterlunch on freenode)

Download the latest build here: http://alureon.net/IRCbutt-latest.jar

##Special Thanks:
- *BullShark* - for ideas, showing me buttbot, and the name 'buttbutt'
- *Klong* - for tons of contributions and several commits
- *ebolahats* - for being a rubber duck
- *MandL27* - for breaking the bot and pointing out what's annoying

##Usage:
Most commands start with `!` followed by the command name, then arguments.

Fact requests start with a `~` followed immediately by the fact name.

You don't include the brackets `<>`

###Basic Functionality
`!8 <question>`         -  Ask the Magic 8 Ball a question

`!g <query>`            -  Google something

`!gi <query>`           -  Google Image Search

`!check <query>`        -  Get a random response on whether or not something passes or fails

`!coin <query>`         -  Get a random heads or tails response

`!echo <query>`         -  Echo something (commands can be echoed with $(command))

`!ud <query>`           -  Search Urban Dictionary

`!dice`                 -  Roll a dice to land on a random user in the room

`!more`                 -  Get more results for facts, quotes, or search results

`!define <query>`       -  Get the definition of a word from Merriam Webster

`!rot13 <query>`        -  Get the rot13 equivalent of `<query>`

`!wr`                   -  Wake the room!  Ping everbody in the room.

`!yt <query>`           -  Search YouTube for `<query>`

`!give <user> <thing>`  -  Give something to someone else

`!version`              -  Get the current bot version

###Math Functionality
`!sqrt <number>`      -  Get the square root of `<number>`

`!pow <num1> <num2>`  -  Get the result of num1^num2

`!eval <expression>`  -  Use Jeval to evaluate an expression. ex: !eval sin(0)

###Fact Functionality
`~<string>`                   -  Any string prefixed with a tilde finds the corresponding fact

`!fact`                       -  Retrieve a random factoid from the database

`!learn <factname> <value>`   -  Write a factoid to the database

`!ff <query>`                 -  Search for a fact by string

`!fi <factnumber>`            -  Get information about a factoid

`!append <factname> <string>` -  Appends `<string>` to a current factoid

`!forget <factname>`          -  Delete a fact from the database (MUST BE CHANNEL OP!)

###Quotegrab Functionality
`!grab <username>`     -  Grab and store the last message from `<user>` in the database

`!rq`                  -  Retrieve a random quote from the database

`!q <username>`        -  Retrieve the last grabbed quote from `<user>`

`!qf <query>`          -  Search for a quote by string query

`!qi <query>`          -  Get information about a quote

`!qsay <quotenum>`     -  Say quote by ID

###Karma Functionality
`<randomstring>++`      -  Increase the karma of `<whatever>`

`<randomstring>--`      -  Decrease the karma of `<whatever>`

`!karma <randomstring>` -  Retrieve the current karma of `<whatever>`