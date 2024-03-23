package cz.trapincho.wynntheorybot.events


import cz.trapincho.wynntheorybot.util.logger
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.net.URLEncoder

class SlashCommandEvent : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "ping" ->
                event.reply("Bot\'s latency is ${event.jda.gatewayPing}ms")
                    .setEphemeral(true)
                    .queue()
            "info" ->
                event.deferReply(true)
                    .addEmbeds(
                        EmbedBuilder()
                            .setTitle("Information about the bot")
                            .setDescription("Bot for the WynnTheory discord server\nMade by TrapinchO")
                            .build()
                    ).queue()

            "help" ->
                event.deferReply(true)
                    .addEmbeds(createHelpEmbed())
                    .queue()

            "oldlore" ->
                event.deferReply()
                    .setContent("Links for the old archived lore")
                    .addActionRow(oldloreButtons)
                    .queue()

            "reworks" ->
                event.deferReply()
                    .setContent("Links for the community lore reworks")
                    .addActionRow(reworkButtons)
                    .addActionRow(reworkButtons2)
                    .queue()

            "worldmap" ->
                event.reply("https://map.wynncraft.com/")
                    .queue()

            "wiki" -> {
                val str = if (event.options.isNotEmpty()) event.options[0].asString else ""
                val page = shortcuts[str.lowercase()]
                    ?: URLEncoder.encode(str, "UTF-8").replace("+", "%20")
                event.reply("https://wynncraft.wiki.gg/wiki/$page").queue()
            }

            else -> logger.error("Unknown slash command interaction: ${event.name}")
        }
    }
}

private val shortcuts = mapOf(
    "tl" to "Timeline",
    "sd" to "Secret_Discoveries",
    "cat" to "Category:Lore",
    "category" to "Category:Lore",
)

private val reworkButtons = listOf(
    Button.link("https://forums.wynncraft.com/threads/complete-rework-of-bobs-lore.287431/", "Bob"),
    Button.link("https://forums.wynncraft.com/threads/jungle-lore-rework.290103/", "Jungle"),
    Button.link("https://forums.wynncraft.com/threads/the-ocean-lore-rework.292151/", "Ocean"),
    Button.link("https://forums.wynncraft.com/threads/gavel-lore-rework.295429/", "Gavel"),
    Button.link("https://forums.wynncraft.com/threads/item-lore-rework.301483/", "Item"),
)
private val reworkButtons2 = listOf(
    Button.link("https://forums.wynncraft.com/threads/ancient-wynnic-language-rework.304579/", "Olmic"),
)

private val oldloreButtons = listOf(
    Button.link("https://web.archive.org/web/20140408052809/http://forums.wynncraft.com/threads/wynn-tales-the-legend-of-bob.381", "Bob"),
    Button.link("https://web.archive.org/web/20140410011458/http://forums.wynncraft.com/threads/wynn-tales-the-noble-house-of-twain.8072/", "Twains"),
    Button.link("https://web.archive.org/web/20220716103023/https://forums.wynncraft.com/threads/nether-beast.186210/", "Nether Beast"),
    Button.link("https://web.archive.org/web/20220716102942/https://wynncraft.fandom.com/wiki/Storyline?diff=prev&oldid=121433", "Storyline"),
)

private fun createHelpEmbed() =
    EmbedBuilder()
        .setTitle("Help")
        .addField("**help**", "Displays list of available commands", false)
        .addField("**info**", "Displays information about the bot", false)
        .addField("**ping**", "Displays bot\'s ping", false)
        .addField("**worldmap**", "Sends link to Wynncraft official online map", false)
        .addField("**oldlore**", "Sends links to old Wynncraft lore", false)
        .addField("**reworks**", "Sends links to old community lore reworks", false)
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
