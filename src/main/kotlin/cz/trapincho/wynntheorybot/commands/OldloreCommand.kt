package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.interactions.components.Button

class OldloreCommand : Command() {
    init {
        this.name = "oldlore"
        this.help = "Sends links to old and outdated lore"
    }
    override fun execute(event: CommandEvent) {
        event.channel.sendMessage("Links for the old archived lore")
            .setActionRow(
                Button.link("https://web.archive.org/web/20140408052809/http://forums.wynncraft.com/threads/wynn-tales-the-legend-of-bob.381", "Bob"),
                Button.link("https://web.archive.org/web/20140410011458/http://forums.wynncraft.com/threads/wynn-tales-the-noble-house-of-twain.8072/", "Twains"),
                Button.link("https://forums.wynncraft.com/threads/nether-beast.186210/", "Nether Beast"),
                Button.link("https://wynncraft.fandom.com/wiki/Storyline?redirect=no", "Storyline"),
            ).queue()
    }
}
