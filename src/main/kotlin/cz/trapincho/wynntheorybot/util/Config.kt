package cz.trapincho.wynntheorybot.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException
import kotlin.system.exitProcess

@Serializable
class Config(
    val tokenPath: String,
    val guestRoleName: String,
) {
    var loggingLevel: String = "INFO"
        set(value) { field = value; saveConfig() }
    var statusText: String = "on developer\'s nerves"
        set(value) { field = value; saveConfig() }

    private fun saveConfig(path: String = defaultConfigPath) {
        File(path).writeText(Json.encodeToString(this))
    }
}

private fun loadConfigFile(path: String = defaultConfigPath): Config {
    try {
        return Json.decodeFromString(File(path).readText())
    } catch (error: FileNotFoundException) {
        logger.error { "Config file not found" }
    } catch (error: SerializationException) {
        logger.error { "Config file is in invalid format." }
    }

    exitProcess(-1)
}

private const val defaultConfigPath = "./config.json"
val config = loadConfigFile()
