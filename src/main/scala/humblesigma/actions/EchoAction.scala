package humblesigma.actions

import humblesigma.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class EchoAction extends Action with Command {

  override final val names = List("echo")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit =
    args match {
      case Some(message) => event.getChannel.sendMessage(message).queue()
      case None => ()
    }

  override val helpMessage: String = "text chat echo"
}
