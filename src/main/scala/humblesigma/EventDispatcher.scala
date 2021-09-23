import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EventDispatcher extends ListenerAdapter {

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {
    handleCommands(event)
  }

  def handleCommands(event: MessageReceivedEvent): Unit = {
    val rawMessage = event.getMessage.getContentRaw
    if (!rawMessage.startsWith("::")) {
      return
    }

    rawMessage.substring(2).toLowerCase() match {
      case "ping" => new PingEvent().handle(event)
      case _ => ()
    }
  }

}
