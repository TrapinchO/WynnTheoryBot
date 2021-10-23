package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import java.net.URLEncoder

class WikiCommand : Command() {
    init {
        this.name = "wiki"
        this.help = "Send a link to requested Wynncraft wiki page (WARNING: case sensitive!)"
        this.arguments = "[page]"
    }
    override fun execute(event: CommandEvent) {
        val page = shortcuts[event.args.lowercase()]
            ?: URLEncoder.encode(event.args, "UTF-8").replace("+", "%20")

        event.channel.sendMessage("https://wynncraft.fandom.com/wiki/$page").queue()
    }

    private val shortcuts = mapOf(
        "tl" to "Timeline",
        "sd" to "Secret Discoveries",
        "cat" to "Category: Lore",
        "category" to "Category: Lore",
    )
}