package humblesigma.actions

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

trait Action {

  def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit

}
