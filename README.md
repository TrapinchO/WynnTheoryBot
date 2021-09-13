# WynnTheory official discord bot

# WARNING
When you build a jar you need to remove certain files from it ortherwise you will be unable to run the jar!

See: [SO answer](https://stackoverflow.com/a/66881564)

## Running the bot
1) create file "config.json" in the same file as the bot jar is in
2) create and populate these fields:
    - authorId: ID of the owner account
    - tokenPath: path to the token
    - prefix: prefix for the bot
    - logging level: logging level for the bot; set to INFO if unsure
    - statusText: sets bot's presence (activity is watching)
3) execute "java -jar <file name>" in command line
