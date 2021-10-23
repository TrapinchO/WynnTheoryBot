package cz.trapincho.wynntheorybot

import ch.qos.logback.classic.Level
import cz.trapincho.wynntheorybot.events.MemberJoinEvents
import cz.trapincho.wynntheorybot.events.SlashCommandEvent
import cz.trapincho.wynntheorybot.util.config
import cz.trapincho.wynntheorybot.util.logger
import cz.trapincho.wynntheorybot.util.setLoggingLevel
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import java.io.File


fun main() {
    val token = File(config.tokenPath).readText()
    val builder = JDABuilder.createLight(token, listOf())
        .setActivity(Activity.watching(config.statusText))
        .addEventListeners(
            SlashCommandEvent(),
            MemberJoinEvents(),
        )

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

    try {
        setLoggingLevel(Level.toLevel(config.loggingLevel))
        logger.info { "Bot successfully started" }

        // run the bot
        val jda = builder
            .build()
            .awaitReady()

        commands.forEach {
            jda.upsertCommand(it)
                .queue()
        }
    } catch (error: Exception) {
        logger.error(error) { "$error" }
    } finally {
        // TODO: Logging file stuff
    }
}
