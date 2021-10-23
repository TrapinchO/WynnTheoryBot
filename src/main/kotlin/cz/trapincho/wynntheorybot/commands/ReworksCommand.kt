package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.interactions.components.Button

class ReworksCommand : Command() {
    init {
        this.name = "reworks"
        this.help = "Sends links for community reworks"
    }

    override fun execute(event: CommandEvent) {
        event.channel.sendMessage("Links for the community lore reworks")
            .setActionRow(
                Button.link("https://forums.wynncraft.com/threads/complete-rework-of-bobs-lore.287431/", "Bob"),
                Button.link("https://forums.wynncraft.com/threads/jungle-lore-rework.290103/", "Jungle"),
                Button.link("https://forums.wynncraft.com/threads/the-ocean-lore-rework.292151/", "Ocean"),
                Button.link("https://forums.wynncraft.com/threads/gavel-lore-rework.295429/", "Gavel"),
            ).queue()
    }
}