package humblesigma.actions

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class HelpAction extends BotAction {
  override final val names = List("help")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit =
    event.getChannel.sendMessage("help").queue()
}
