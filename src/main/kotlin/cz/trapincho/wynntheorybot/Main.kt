package cz.trapincho.wynntheorybot

import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import ch.qos.logback.classic.Level
import cz.trapincho.wynntheorybot.commands.*
import cz.trapincho.wynntheorybot.commands.technical.*
import cz.trapincho.wynntheorybot.events.Events
import cz.trapincho.wynntheorybot.util.config
import cz.trapincho.wynntheorybot.util.logger
import cz.trapincho.wynntheorybot.util.setLoggingLevel
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.components.Button
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.io.File
import java.net.URLEncoder


fun main() {
    val token = File(config.tokenPath).readText()
    val jda = JDABuilder.createLight(token, listOf())
        .setActivity(Activity.watching("over the WynnTheory discord"))
        .addEventListeners(SlashListener(), Events())
        .build()
        .awaitReady()

    logger.info { "Bot successfully started" }
    val commands = listOf(
        CommandData("ping", "Sends bot's latency"),
        CommandData("info", "Sends information about the bot"),
        CommandData("help", "Displays list of available commands"),

        CommandData("oldlore", "Sends links to old and outdated lore"),
        CommandData("reworks", "Sends links for community reworks"),

        CommandData("worldmap", "Sends link to Wynncraft official online map"),
        CommandData("wiki", "Send a link to requested Wynncraft wiki page (WARNING: case sensitive!)")
            .addOption(OptionType.STRING, "page", "what page to show"),
    )
    commands.forEach {
        jda.getGuildById("590995955214450693")
            ?.upsertCommand(it)
            ?.queue()
    }
}

class SlashListener : ListenerAdapter() {
    override fun onSlashCommand(event: SlashCommandEvent) {
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
                    .addActionRow(
                        Button.link("https://web.archive.org/web/20140408052809/http://forums.wynncraft.com/threads/wynn-tales-the-legend-of-bob.381", "Bob"),
                        Button.link("https://web.archive.org/web/20140410011458/http://forums.wynncraft.com/threads/wynn-tales-the-noble-house-of-twain.8072/", "Twains"),
                        Button.link("https://forums.wynncraft.com/threads/nether-beast.186210/", "Nether Beast"),
                        Button.link("https://wynncraft.fandom.com/wiki/Storyline?redirect=no", "Storyline"),
                    ).queue()

            "reworks" ->
                event.deferReply()
                    .setContent("Links for the community lore reworks")
                    .addActionRow(
                        Button.link("https://forums.wynncraft.com/threads/complete-rework-of-bobs-lore.287431/", "Bob"),
                        Button.link("https://forums.wynncraft.com/threads/jungle-lore-rework.290103/", "Jungle"),
                        Button.link("https://forums.wynncraft.com/threads/the-ocean-lore-rework.292151/", "Ocean"),
                        Button.link("https://forums.wynncraft.com/threads/gavel-lore-rework.295429/", "Gavel"),
                    ).queue()

            "worldmap" ->
                event.reply("https://map.wynncraft.com/")
                    .queue()

            "wiki" -> {
                val str = if (event.options.isNotEmpty()) event.options[0].asString else ""
                val page = shortcuts[str.lowercase()]
                    ?: URLEncoder.encode(str, "UTF-8").replace("+", "%20")
                event.reply("https://wynncraft.fandom.com/wiki/$page").queue()
            }

            else -> logger.warn { "Unknown slash command interaction: ${event.name}" }
        }
    }
}
private val shortcuts = mapOf(
    "tl" to "Timeline",
    "sd" to "Secret_Discoveries",
    "cat" to "Category:Lore",
    "category" to "Category:Lore",
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

//fun main1() {
//    val token = File(config.tokenPath).readText()
//    val waiter = EventWaiter()
//    val client = CommandClientBuilder()
//        .setOwnerId(config.authorId)
//        .setPrefix(config.prefix)
//        .setActivity(Activity.watching(config.statusText))
//        .useHelpBuilder(false)
//
//        .addCommands(
//            ShutdownCommand(),
//            LogCommand(),
//
//            HelpCommand(),
//            InfoCommand(),
//            PingCommand(),
//
//            WorldmapCommand(),
//            OldloreCommand(),
//            ReworksCommand(),
//            WikiCommand(),
//            TheoriesCommand(),
//        )
//
//    val builder = JDABuilder.createDefault(token, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
//        .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
//
//        .addEventListeners(Events())
//        .addEventListeners(waiter, client.build())
//
//    try {
//        setLoggingLevel(Level.toLevel(config.loggingLevel))
//        logger.info { "Bot successfully started" }
//
//        builder.build()  // run the bot
//    } catch (error: Exception) {
//        logger.error(error) { "$error" }
//    } finally {
//        // TODO: Logging file stuff
//    }
//}