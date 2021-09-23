package humblesigma.actions

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class EchoAction extends BotAction {

  override final val names = List("echo")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit =
    args match {
      case Some(message) => event.getChannel.sendMessage(message).queue()
      case None => ()
    }

}