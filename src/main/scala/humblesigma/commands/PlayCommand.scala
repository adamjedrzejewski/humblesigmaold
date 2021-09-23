package humblesigma.commands
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class PlayCommand extends BotCommand {
  override val command: String = "play"

  override def handle(event: MessageReceivedEvent, command: String, args: Option[String]): Unit = {
    event.getChannel.sendMessage("play").queue()
  }
}
