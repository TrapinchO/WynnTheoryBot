package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder

class HelpCommand : Command() {
    init {
        this.name = "help"
        this.help = "Displays list of available commands"
    }
    override fun execute(event: CommandEvent) {
        // gets the command name from the name of its class
        // println(Regex("^.*\\.(.*)Command$").find(this.javaClass.name)?.groupValues?.get(1)?.lowercase())
        event.channel.sendMessage(
            EmbedBuilder()
            .setTitle("Help")
                .addField("**help**", "Displays list of available commands", false)
                .addField("**info**", "Displays information about the bot", false)
                .addField("**ping**", "Displays bot\'s ping", false)
                .addField("**worldmap**", "Sends link to Wynncraft official online map", false)
                .addField("**oldlore**", "Sends links to old Wynncraft lore", false)
                .addField(
                   "**wiki [page]**",
                   "Sends link to requested page on Wynncraft wiki" +
                           "*(WARNING: case sensitive!)*" +
                           "\nShortcuts available:" +
                           "\n- tl = Timeline" +
                           "\n- sd = Secret Discoveries" +
                           "\n- cat/category = Category: Lore (category of all lore pages)"
                    , false)
                .addField("**theories [name]**", "Sends either list of theories or link to requested theory", false)
            .build()
        ).queue()
    }
}