package humblesigma.actions

import humblesigma.Command
import humblesigma.voice.music.PlayerManager
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class NowAction extends Command with Action {
  override val names: List[String] = List("now")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val musicManager = PlayerManager.getMusicManager(event.getGuild)
    val currentTrackInfo = musicManager.audioPlayer.getPlayingTrack.getInfo
    event.getChannel.sendMessage("Now playing: ")
      .append(s"`${currentTrackInfo.title}`")
      .queue()
  }

  override val helpMessage: String = "show currently played song"
}
