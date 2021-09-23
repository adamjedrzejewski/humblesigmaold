package humblesigma.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class EchoCommand extends BotCommand {

  override final val command = "echo"

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val message = args match {
      case Some(x) => x
      case None => return
    }

    event.getChannel.sendMessage(message).queue()
  }

}
