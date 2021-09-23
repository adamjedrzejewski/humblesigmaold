package humblesigma.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

trait BotCommand {
  val command: String
  def handle(event: MessageReceivedEvent): Unit
  // def handle(event: MessageReceivedEvent, command: String, args: List[String]): Unit
}
