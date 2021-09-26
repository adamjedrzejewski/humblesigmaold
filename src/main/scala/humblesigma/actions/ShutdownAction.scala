package humblesigma.actions
import humblesigma.Configuration
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class ShutdownAction(configuration: Configuration) extends BotAction {
  override val names: List[String] = List("shutdown")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    configuration.owner match {
      case Some(owner) =>
        val id = event.getAuthor.getId
        if (owner == id) {
          event.getChannel.sendMessage("Shutting down").queue()
          println("Shutting down")
          System.exit(0)
        }
      case None => ()
    }
  }
}
