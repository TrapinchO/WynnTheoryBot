package cz.trapincho.wynntheorybot.commands.technical

import ch.qos.logback.classic.Level
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import cz.trapincho.wynntheorybot.util.setLoggingLevel
import java.io.File


class LogCommand : Command() {
    init {
        this.name = "log"
        this.help = "Command for managing logging"
        this.arguments = "<retrieve|level> [level]"

        this.hidden = true
        this.requiredRole = "Admin"
    }
    override fun execute(event: CommandEvent) {
        if (event.args.isEmpty()) {
            event.channel.sendMessage("No arguments were given").queue()
            return
        }
        val args = event.args.split(" ")
        if (args[0] == "retrieve")
            event.channel.sendFile(File("bot.log"), "bot.log").queue()

        else if (args[0] == "level") {
            if (args.size < 2) event.channel.sendMessage("Insufficient arguments were given").queue()
            else {
                setLoggingLevel(Level.toLevel(args[1]))
                event.channel.sendMessage("Attempted to set logging level to `${args[1]}`").queue()
            }
        } else event.channel.sendMessage("Invalid option").queue()
    }
}
