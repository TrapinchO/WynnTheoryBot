package cz.trapincho.wynntheorybot.events

import cz.trapincho.wynntheorybot.util.config
import cz.trapincho.wynntheorybot.util.getTheoryEntries
import cz.trapincho.wynntheorybot.util.logger
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.entity.Message
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.message.create.actionRow
import java.io.File
import java.net.URLEncoder

fun onMessageCreate(client: Kord) = client.on<MessageCreateEvent> {
    if(message.author?.isBot != false)
        return@on
    if (message.content.isNotEmpty() && message.content[0].lowercase() != config.prefix)
        return@on

    when (val command = message.content.lowercase().substring(1).split(" ")[0]) {
        "info" -> message.channel.createEmbed {
            title = "Information about the bot"
            description = "Bot for the WynnTheory discord server\nMade by TrapinchO"
        }
        "ping" -> message.channel.createMessage("Bot\'s latency is ${message.kord.gateway.averagePing}")
        "worldmap" -> message.channel.createMessage("https://map.wynncraft.com/")

        "help" -> message.channel.createEmbed {
            field("**help**") { "Displays list of available commands" }
            field("**info**") { "Displays information about the bot" }
            field("**ping**") { "Displays bot\'s ping" }
            field("**worldmap**") { "Sends link to Wynncraft official online map" }
            field("**oldlore**") { "Sends links to old Wynncraft lore" }
            field("**reworks**") { "Sends links community lore reworks" }
            field("**theories**") { "Sends either list of theories or link to requested theory" }
            field("**wiki**") { "Sends link to requested page on Wynncraft wiki" +
                    "*(WARNING: case sensitive!)*" +
                    "\nShortcuts available:" +
                    "\n- tl = Timeline" +
                    "\n- sd = Secret Discoveries" +
                    "\n- cat/category = Category: Lore (category of all lore pages)" }
        }

        "oldlore" -> {
            message.channel.createMessage {
                content = "Links for the old and outdated archived lore"
                actionRow {
                    linkButton("https://web.archive.org/web/20140408052809/http://forums.wynncraft.com/threads/wynn-tales-the-legend-of-bob.381") { label = "Bob" }
                    linkButton("https://web.archive.org/web/20140410011458/http://forums.wynncraft.com/threads/wynn-tales-the-noble-house-of-twain.8072/") { label = "Twains" }
                    linkButton("https://forums.wynncraft.com/threads/nether-beast.186210/") { label = "Nether Beast" }
                    linkButton("https://wynncraft.fandom.com/wiki/Storyline?redirect=no") { label = "Storyline" }
                }
            }
        }

        "reworks" -> message.channel.createMessage {
            content = "Links for the community lore reworks"
            actionRow {
                val base = "https://forums.wynncraft.com/threads/"
                linkButton(base + "complete-rework-of-bobs-lore.287431/") { label = "Bob" }
                linkButton(base + "jungle-lore-rework.290103/") { label = "Jungle" }
                linkButton(base + "the-ocean-lore-rework.292151/") { label = "Ocean" }
                linkButton(base + "gavel-lore-rework.295429/") { label = "Gavel" }
            }
        }

        "wiki" -> {
            val name = if (message.content.length < 6) "" else getWikiPage(message.content.substring(6))
            message.channel.createMessage("https://wynncraft.fandom.com/wiki/$name")
        }

        "theories" -> {
            val args = if (message.content.length < 10) "" else message.content.substring(10)

            theories(message, args)
        }

        "shutdown" -> {
            if (message.author?.id?.asString == config.authorId) {
                logger.error { "Closing the bot because of user command" }
                message.kord.shutdown()
            }
        }

        "log" -> {
            if (message.author?.id?.asString == config.authorId) {
                message.channel.createMessage {
                    files.add("bot.log" to File("bot.log").inputStream())
                }
            }
        }

        else -> message.channel.createMessage("Unknown command: \"$command\"")
    }
}


private val wikiShortcuts = mapOf(
    "tl" to "Timeline",
    "sd" to "Secret Discoveries",
    "cat" to "Category: Lore",
    "category" to "Category: Lore",
)

private fun getWikiPage(name: String): String =
    URLEncoder.encode(wikiShortcuts[name.lowercase()] ?: name, "UTF-8").replace("+", "%20")


private suspend fun theories(message: Message, args: String) {
    val entries = getTheoryEntries()

    if (entries.isEmpty())
        message.channel.createMessage("No entries found")

    if (args == "") {
        message.channel.createEmbed {
            title = "Small lore entries"
            entries.forEach { entry ->
                field { name = "\"${entry.name}\" by \"${entry.author}\""; value = entry.link }
            }
        }
    } else {
        val entry = entries.find { it.name.lowercase() == args.lowercase() }

        message.channel.createMessage(
            if (entry == null) "Couldn\'t find entry with that name"
            else "`\"${entry.name}\" by \"${entry.author}\"`\n${entry.link}"
        )
    }
}