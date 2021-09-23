import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class BotTest extends ListenerAdapter {
  //  override def onMessageReceived(event): Any = {
  //    event.getName match {
  //      case "ping" =>
  //        val time = System.currentTimeMillis
  //        event.reply("pong").setEphemeral(true).flatMap(
  //          { _ => event.getHook.editOriginalFormat(f"Pong: $time ms")}
  //        ).queue()
  //      case _ => ()
  //    }
  //  }
  override def onMessageReceived(event: MessageReceivedEvent): Unit = {
    val msg = event.getMessage
    msg.getContentRaw match {
      case "!ping" =>
        val channel = event.getChannel
        val time = System.currentTimeMillis
        channel.sendMessage("Pong").queue({
            response => response.editMessageFormat(s"Pong ${System.currentTimeMillis - time}").queue()
          })

      case _ => ()
    }
  }
}
