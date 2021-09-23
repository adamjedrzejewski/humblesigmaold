package humblesigma.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

trait BotCommand {

  // val names: List[String]
  // val help: String
  val command: String

  def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit

}
