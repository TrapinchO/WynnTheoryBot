package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class WorldmapCommand : Command() {
    init {
        this.name = "worldmap"
        this.help = "Sends link to Wynncraft official online map"
    }
    override fun execute(event: CommandEvent) {
        event.channel.sendMessage("https://map.wynncraft.com/")
            .queue()
    }
}