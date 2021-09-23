package humblesigma.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class PingAction extends BotAction {

  override final val names = List("ping")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    event.getChannel.sendMessage("Pong").queue()
  }

}
