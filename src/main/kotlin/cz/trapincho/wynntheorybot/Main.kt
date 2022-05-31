package cz.trapincho.wynntheorybot

import cz.trapincho.wynntheorybot.events.MemberJoinEvents
import cz.trapincho.wynntheorybot.events.SlashCommandEvent
import cz.trapincho.wynntheorybot.util.config
import cz.trapincho.wynntheorybot.util.logger
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.internal.interactions.CommandDataImpl
import java.io.File


fun main() {
    val token = File(config.tokenPath).readText()
    val builder = JDABuilder.createLight(token, listOf(GatewayIntent.GUILD_MEMBERS))
        .setActivity(Activity.watching(config.statusText))
        .addEventListeners(
            SlashCommandEvent(),
            MemberJoinEvents(),
        )

    val commands = listOf(
        CommandDataImpl("ping", "Sends bot's latency"),
        CommandDataImpl("info", "Sends information about the bot"),
        CommandDataImpl("help", "Displays list of available commands"),

        CommandDataImpl("oldlore", "Sends links to old and outdated lore"),
        CommandDataImpl("reworks", "Sends links for community reworks"),

        CommandDataImpl("worldmap", "Sends link to Wynncraft official online map"),
        CommandDataImpl("wiki", "Send a link to requested Wynncraft wiki page (WARNING: case sensitive!)")
            .addOption(OptionType.STRING, "page", "what page to show"),
    )

    try {
        //setLoggingLevel(Level.toLevel(config.loggingLevel))
        logger.info { "Bot successfully started" }

        // run the bot
        val jda = builder
            .build()
            .awaitReady()

        commands.forEach {
            jda.upsertCommand(it).queue()
        }
    } catch (error: Exception) {
        logger.error(error) { "$error" }
    } finally {
        // TODO: Logging file stuff
    }
}
