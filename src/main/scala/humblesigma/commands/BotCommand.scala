package humblesigma.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

trait BotCommand {
  val command: String
  // val names: List[String]
  def handle(event: MessageReceivedEvent, command: String, args: Option[String]): Unit
}
