package cz.trapincho.wynntheorybot

import ch.qos.logback.classic.Level
import cz.trapincho.wynntheorybot.events.*
import cz.trapincho.wynntheorybot.util.config
import cz.trapincho.wynntheorybot.util.logger
import cz.trapincho.wynntheorybot.util.setLoggingLevel
import dev.kord.core.Kord
import java.io.File


suspend fun main() {
    val token = File(config.tokenPath).readText()
    val client = Kord(token)

    // import events
    onReady(client)
    onMemberJoin(client)
    onMessageCreate(client)

    try {
        setLoggingLevel(Level.toLevel(config.loggingLevel))
        logger.info { "Bot successfully started" }

        client.login()  // run the bot
    } catch (error: Exception) {
        logger.error(error) { "$error" }
    } finally {
        // TODO: Logging file stuff
    }
}