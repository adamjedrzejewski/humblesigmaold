package humblesigma.actions

import humblesigma.Command
import humblesigma.voice.music.PlayerManager
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class ClearAction extends Command with Action {
  override val names: List[String] = List("clear")
  override val helpMessage: String = "clear playing queue"

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val channel = event.getChannel
    PlayerManager.clearPlayQueue(channel)
    PlayerManager.skipSong(channel)
  }
}
