package cz.trapincho.wynntheorybot.commands.technical

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import cz.trapincho.wynntheorybot.util.logger

class ShutdownCommand : Command() {
    init {
        this.name = "shutdown"
        this.help = "Shuts down the bot. Only available for admins"

        this.hidden = true
        this.requiredRole = "Admin"
    }

    override fun execute(event: CommandEvent) {
        event.jda.shutdown()
        logger.error { "Closing the bot because of user command" }
    }
}