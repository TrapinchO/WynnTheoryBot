package cz.trapincho.wynntheorybot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class PingCommand : Command() {
    init {
        this.name = "ping"
        this.help = "Sends bot\'s latency"
    }
    override fun execute(event: CommandEvent) {
        event.channel.sendMessage("Bot\'s latency is ${event.jda.gatewayPing}ms")
            .queue()
    }
}