package humblesigma.commands
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class HelpCommand extends BotCommand {
  override final val command: String = "help"

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    event.getChannel.sendMessage("help").queue()
  }
}
