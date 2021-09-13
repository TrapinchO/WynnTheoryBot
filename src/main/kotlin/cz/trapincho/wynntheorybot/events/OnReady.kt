package cz.trapincho.wynntheorybot.events

import cz.trapincho.wynntheorybot.util.config
import dev.kord.core.Kord
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.on

fun onReady(client: Kord) = client.on<ReadyEvent> {
    kord.editPresence { watching(config.statusText) }
}