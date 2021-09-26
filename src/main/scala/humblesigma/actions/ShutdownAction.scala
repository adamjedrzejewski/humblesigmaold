package humblesigma.actions

import humblesigma.{Command, Configuration}
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class ShutdownAction(configuration: Configuration) extends Action with Command {
  override val names: List[String] = List("shutdown")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val textChannel = event.getChannel
    configuration.owner match {
      case Some(owner) => perform(owner, event.getAuthor.getId, textChannel)
      case None => textChannel.sendMessage("No owner specified in config").queue()
    }
  }

  private def perform(ownerId: String, user: String, textChannel: TextChannel): Unit = {
    if (ownerId == user) {
      textChannel.sendMessage("Shutting down").queue()
      Thread.sleep(500)
      System.exit(0)
    } else {
      textChannel.sendMessage("You are not the owner, only owner can use this command").queue()
    }
  }

}
