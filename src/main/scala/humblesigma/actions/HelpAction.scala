package humblesigma.actions

import humblesigma.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class HelpAction extends Action with Command {
  override final val names = List("help")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit =
    event.getChannel.sendMessage("help").queue()
}
