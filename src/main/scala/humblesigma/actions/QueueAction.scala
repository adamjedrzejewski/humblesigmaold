package humblesigma.actions

import humblesigma.Command
import humblesigma.voice.music.PlayerManager
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class QueueAction extends Action with Command {

  override val names: List[String] = List("queue", "q")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val musicManager = PlayerManager.getMusicManager(event.getGuild)
    val list = musicManager.scheduler.getQueuedTracks
    val currentTrack = musicManager.audioPlayer.getPlayingTrack
    val channel = event.getChannel

    val action = channel.sendMessage("Now playing: ")
      .append(s"`${currentTrack.getInfo.title}`\n")
      .append('\n')

    action.append("Queue:\n")
    list.zipWithIndex.foreach {
      case (track, i) =>
        val trackInfo = track.getInfo
        action.append(s"`${i + 1}. ${trackInfo.title}`\n")
    }

    action.queue()
  }

}
