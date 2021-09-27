package humblesigma.actions

import humblesigma.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class PingAction extends Action with Command {

  override final val names = List("ping")
  override val helpMessage: String = "pong"

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    event.getChannel.sendMessage("pong").queue()
  }
}
