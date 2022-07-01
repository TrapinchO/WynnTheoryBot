package cz.trapincho.wynntheorybot

import cz.trapincho.wynntheorybot.events.MemberJoinEvents
import cz.trapincho.wynntheorybot.events.SlashCommandEvent
import cz.trapincho.wynntheorybot.util.config
import cz.trapincho.wynntheorybot.util.logger
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File
import kotlin.concurrent.timer


fun main() {
    val token = File(config.tokenPath).readText()
    val builder = JDABuilder.createLight(token, listOf(GatewayIntent.GUILD_MEMBERS))
        .setActivity(Activity.watching(config.statusText))
        .addEventListeners(
            SlashCommandEvent(),
            MemberJoinEvents(),
        )

    val commands = listOf(
        Commands.slash("ping", "Sends bot's latency"),
        Commands.slash("info", "Sends information about the bot"),
        Commands.slash("help", "Displays list of available commands"),

        Commands.slash("oldlore", "Sends links to old and outdated lore"),
        Commands.slash("reworks", "Sends links for community reworks"),

        Commands.slash("worldmap", "Sends link to Wynncraft official online map"),
        Commands.slash("wiki", "Send a link to requested Wynncraft wiki page (WARNING: case sensitive!)")
            .addOption(OptionType.STRING, "page", "what page to show"),
    )

    try {
        //setLoggingLevel(Level.toLevel(config.loggingLevel))
        logger.info("Bot successfully started")

        // run the bot
        val jda = builder
            .build()
            .awaitReady()

        jda.updateCommands()
            .addCommands(commands)
            .queue()

        // check every four hours that the bot runs
        timer("regular log event", true, 0, 1000*60*60*4) {
            logger.info("regular check")
        }

    } catch (err: Exception) {
        logger.error(err) { "$err" }
    } finally {
        // TODO: Logging file stuff
    }
}
