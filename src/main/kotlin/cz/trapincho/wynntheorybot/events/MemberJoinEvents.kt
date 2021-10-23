package cz.trapincho.wynntheorybot.events

import cz.trapincho.wynntheorybot.util.logger
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MemberJoinEvents : ListenerAdapter() {
    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        // if "Guest" role is not found report an error and return
        val role = event.guild.roles.find { role -> role.name == "Guest" }
            ?: return logger.error { "Role \"Guest\" not found" }
        event.guild.addRoleToMember(event.member, role).queue()
    }
}