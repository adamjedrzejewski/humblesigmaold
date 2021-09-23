package humblesigma.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class PingCommand extends BotCommand {

  override final val command = "ping"

  override def handle(event: MessageReceivedEvent, command: String, args: Option[String]): Unit = {
    event.getChannel.sendMessage("Pong").queue()
  }

}
