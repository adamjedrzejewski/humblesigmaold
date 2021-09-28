package humblesigma.actions

import humblesigma.Command
import humblesigma.voice.music.PlayerManager
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class NowAction extends Command with Action {
  override val names: List[String] = List("now")
  override val helpMessage: String = "show currently played song"

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val musicManager = PlayerManager.getMusicManager(event.getGuild)
    val currentTrack = musicManager.audioPlayer.getPlayingTrack
    if (currentTrack != null) {
      event.getChannel.sendMessage("Now playing: ")
        .append(s"`${currentTrack.getInfo.title}`")
        .queue()
    } else {
      event.getChannel.sendMessage("Not playing anything right now")
        .queue()
    }
  }
}
