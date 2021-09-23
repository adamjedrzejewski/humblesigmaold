package humblesigma.actions

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

trait BotAction {

  val names: List[String]
  // val help: String
  //val command: String

  def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit

}
