package humblesigma.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class EchoCommand extends BotCommand {

  override final val command = "echo"

  override def handle(event: MessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val message = args match {
      case Some(x) => x
      case None => ""
    }
    event.getChannel.sendMessage(message).queue()
  }

}
