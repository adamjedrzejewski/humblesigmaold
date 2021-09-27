package humblesigma.voice.music

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.{AudioPlaylist, AudioTrack}
import net.dv8tion.jda.api.entities.TextChannel

class MusicLoadResultHandler(musicManager: GuildMusicManager, channel: TextChannel) extends AudioLoadResultHandler {

  override def trackLoaded(track: AudioTrack): Unit = {
    musicManager.scheduler.addTrackToQueue(track)
    channel.sendMessage("Adding to queue `")
      .append(track.getInfo.title)
      .append("` by `")
      .append(track.getInfo.author)
      .append("`")
      .queue()
  }

  override def playlistLoaded(playlist: AudioPlaylist): Unit = {}

  override def noMatches(): Unit = {}

  override def loadFailed(exception: FriendlyException): Unit = {}
}
