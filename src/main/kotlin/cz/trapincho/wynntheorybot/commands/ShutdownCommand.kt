package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import cz.trapincho.wynntheorybot.util.logger

class ShutdownCommand : Command() {
    init {
        this.name = "shutdown"
        this.help = "Shuts down the bot. Only available for admins"
        this.hidden = true
    }

    override fun execute(event: CommandEvent) {
        val role = event.guild.roles.find { role -> role.name == "Admin" } ?: return

        if (!event.message.member?.roles?.contains(role)!!) return

        event.jda.shutdown()
        logger.error { "Closing the bot because of user command" }
    }
}