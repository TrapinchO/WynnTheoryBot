package cz.trapincho.wynntheorybot.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

/** Helper class to get list of entries */
@Serializable
class TheoryList(val data: List<TheoryEntry>)

@Serializable
class TheoryEntry(
    val name: String,
    val author: String,
    val link: String,
    val update: String, // during what update was the theory created
    val date: String,
    val topics: List<String>
)

fun getTheoryEntries(): List<TheoryEntry> {
    // get the theories in json
    val text = URL("https://raw.githubusercontent.com/TrapinchO/trapincho.github.io/main/wynn/wynn_theory.json").readText()

    // serialize to data classes
    // a little hack because serialization doesn't seem to be able to serialize arrays...
    return Json.decodeFromString<TheoryList>("{\"data\": $text}").data  // Get the entries from json
}