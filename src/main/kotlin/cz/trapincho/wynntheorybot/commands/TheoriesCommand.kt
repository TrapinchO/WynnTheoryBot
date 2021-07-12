package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import cz.trapincho.wynntheorybot.util.getTheoryEntries
import net.dv8tion.jda.api.EmbedBuilder

class TheoriesCommand : Command() {
    init {
        this.name = "theories"
        this.help = "Sends links for community theories"
        this.arguments = "[theory name]"
    }

    override fun execute(event: CommandEvent) {
        val entries = getTheoryEntries()

        if (entries.isEmpty())
            event.channel.sendMessage(EmbedBuilder().setTitle("No entries found").build()).queue()

        if (event.args == "") {
            val embed = EmbedBuilder()
                .setTitle("Small lore entries")

            for (entry in entries)
                embed.addField("\"${entry.name}\" by \"${entry.author}\"", entry.link, false)

            event.channel.sendMessage(embed.build()).queue()
        } else {
            val entry = entries.find { it.name.lowercase() == event.args.lowercase() }

            if (entry == null) {
                event.channel.sendMessage(
                    EmbedBuilder()
                        .setDescription("Couldn\'t find entry with that name")
                        .build()
                ).queue()
                return
            }

            event.channel.sendMessage(
                EmbedBuilder()
                    .addField("\"${entry.name}\" by \"${entry.author}\"", entry.link, false)
                    .build()
            ).queue()
        }
    }
}