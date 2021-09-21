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
        // sanitation of the page string for later usage in url
        var page = URLEncoder.encode(event.args, "UTF-8").replace("+", "%20")

        if (page in shortcuts) {
            page = shortcuts[page]!!  // can't actually raise an error, since there is a check right before it
        }
        // sends link to home page if no arguments are present
        event.channel.sendMessage("https://wynncraft.fandom.com/wiki/$page").queue()
    }

    private val shortcuts = mapOf(
        "tl" to "Timeline",
        "sd" to "Secret Discoveries",
        "cat" to "Category: Lore",
        "category" to "Category: Lore",
    )
}