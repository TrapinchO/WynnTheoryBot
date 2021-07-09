package cz.trapincho.wynntheorybot

import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import ch.qos.logback.classic.Level
import cz.trapincho.wynntheorybot.commands.*
import cz.trapincho.wynntheorybot.events.Events
import cz.trapincho.wynntheorybot.util.logger
import cz.trapincho.wynntheorybot.util.setLoggingLevel
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.io.File

fun main(args: Array<String>) {
    val tokenPath = if (args.isNotEmpty()) args[0] else "../discord_token.txt" // default is for testing
    val token = File(tokenPath).readText()

    val waiter = EventWaiter()
    val client = CommandClientBuilder()
        .setOwnerId("552883527147061249")  // TrapinchO rn
        .setPrefix("!")
        .setActivity(Activity.playing("on developer\'s nerves"))
        .useHelpBuilder(false)

        .addCommands(
            ShutdownCommand(),
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
        setLoggingLevel(Level.INFO)
        logger.info { "Bot successfully started" }

        builder.build()  // run the bot
    } catch (error: Exception) {
        logger.error(error) { "$error" }
    }
}