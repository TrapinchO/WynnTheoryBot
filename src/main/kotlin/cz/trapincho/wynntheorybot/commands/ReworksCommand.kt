package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder

class ReworksCommand : Command() {
    init {
        this.name = "reworks"
        this.help = "Sends links for community reworks"
    }

    override fun execute(event: CommandEvent) {
        event.channel.sendMessage(
            EmbedBuilder()
                .setTitle("Lore rework links")
                .setDescription("Links for the community lore reworks")
                .addField("Bob", "https://forums.wynncraft.com/threads/complete-rework-of-bobs-lore.287431/", false)
                .addField("Jungle", "https://forums.wynncraft.com/threads/jungle-lore-rework.290103/", false)
                .addField("Ocean", "https://forums.wynncraft.com/threads/the-ocean-lore-rework.292151/#post-3448277", false)
                .addField("Gavel", "To be finished", false)
                .build()
        ).queue()
    }
}