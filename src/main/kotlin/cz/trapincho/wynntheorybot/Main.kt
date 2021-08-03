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
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.io.File

fun main(args: Array<String>) {
    val token = File(config.tokenPath).readText()
    val waiter = EventWaiter()
    val client = CommandClientBuilder()
        .setOwnerId(config.authorId)
        .setPrefix(config.prefix)
        .setActivity(Activity.watching(config.statusText))
        .useHelpBuilder(false)

        .addCommands(
            ShutdownCommand(),
            LogCommand(),

            HelpCommand(),
            InfoCommand(),
            PingCommand(),

            WorldmapCommand(),
            OldloreCommand(),
            ReworksCommand(),
            WikiCommand(),
            TheoriesCommand(),
        )

    val builder = JDABuilder.createDefault(token, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
        .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)

        .addEventListeners(Events())
        .addEventListeners(waiter, client.build())

    try {
        setLoggingLevel(Level.toLevel(config.loggingLevel))
        logger.info { "Bot successfully started" }

        builder.build()  // run the bot
    } catch (error: Exception) {
        logger.error(error) { "$error" }
    } finally {
        // TODO: Logging file stuff
    }
}