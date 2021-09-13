package cz.trapincho.wynntheorybot.events

import cz.trapincho.wynntheorybot.util.logger
import dev.kord.core.Kord
import dev.kord.core.event.guild.MemberJoinEvent
import dev.kord.core.on
import kotlinx.coroutines.flow.toList

fun onMemberJoin(client: Kord) = client.on<MemberJoinEvent> {
    val role = member.guild.roles.toList().find { role -> role.name == "Guest" }
        ?: return@on logger.error { "Role \"Guest\" not found" }

    member.addRole(role.id)
}