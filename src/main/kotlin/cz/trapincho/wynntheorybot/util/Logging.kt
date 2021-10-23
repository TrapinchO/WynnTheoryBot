package cz.trapincho.wynntheorybot.util

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import mu.KotlinLogging
import org.slf4j.LoggerFactory

val logger = KotlinLogging.logger {}

/**
 * Note: doesn't set the level in config
 * TODO: Solve
 */
fun setLoggingLevel(level: Level) =
    (LoggerFactory.getILoggerFactory() as LoggerContext).loggerList.forEach {
        it.level = level
    }