package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder

class InfoCommand : Command() {
    init {
        this.name = "info"
        this.help = "Sends information about the bot"
    }
    override fun execute(event: CommandEvent) {
        event.channel.sendMessageEmbeds(
            EmbedBuilder()
            .setTitle("Information about the bot")
            .setDescription("Bot for the WynnTheory discord server\nMade by TrapinchO")
            .build()
        ).queue()
    }
}