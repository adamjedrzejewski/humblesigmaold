package humblesigma.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class PingCommand extends BotCommand {

  final val command = "ping"

  override def handle(event: MessageReceivedEvent): Unit = {
    event.getChannel.sendMessage("Pong").queue()
  }

}
