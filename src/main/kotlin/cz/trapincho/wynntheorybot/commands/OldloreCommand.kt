package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder

class OldloreCommand : Command() {
    init {
        this.name = "oldlore"
        this.help = "Sends links to old and outdated lore"
    }
    override fun execute(event: CommandEvent) {
        event.channel.sendMessage(
            EmbedBuilder()
                .setTitle("Old lore links")
                .setDescription("Links for the old archived lore")
                .addField("Bob", "https://web.archive.org/web/20140408052809/http://forums.wynncraft.com/threads/wynn-tales-the-legend-of-bob.381", false)
                .addField("Twains", "https://web.archive.org/web/20140410011458/http://forums.wynncraft.com/threads/wynn-tales-the-noble-house-of-twain.8072/", false)
                .addField("Nether beast", "https://forums.wynncraft.com/threads/nether-beast.186210/", false)
                .addField("Old storyline", "https://wynncraft.fandom.com/wiki/Storyline?redirect=no", false)
                .build()
        ).queue()
    }
}
