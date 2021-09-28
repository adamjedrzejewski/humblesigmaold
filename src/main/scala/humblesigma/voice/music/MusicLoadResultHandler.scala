package humblesigma.voice.music

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.{AudioPlaylist, AudioTrack}
import net.dv8tion.jda.api.entities.TextChannel

import scala.collection.JavaConverters._

class MusicLoadResultHandler(musicManager: GuildMusicManager, channel: TextChannel) extends AudioLoadResultHandler {

  private val playlistMaxSize = 25

  override def playlistLoaded(playlist: AudioPlaylist): Unit = {
    if (playlist.getTracks.size() > playlistMaxSize) {
      channel.sendMessage(s"Playlist longer than max, adding first $playlistMaxSize elements").queue()
    }

    val tracks = playlist.getTracks.asScala.toList.take(playlistMaxSize)

    if (playlist.isSearchResult) {
      val track = tracks.head
      trackLoaded(track)
    } else {
      channel.sendMessage(s"Adding `${tracks.size}` tracks to queue")
        .queue()

      tracks.foreach {
        track => musicManager.scheduler.addTrackToQueue(track)
      }
    }
  }

  override def trackLoaded(track: AudioTrack): Unit = {
    musicManager.scheduler.addTrackToQueue(track)
    channel.sendMessage("Adding to queue `")
      .append(track.getInfo.title)
      .append("` by `")
      .append(track.getInfo.author)
      .append("`")
      .queue()
  }

  override def noMatches(): Unit = {
    channel.sendMessage("No search result").queue()
  }

  override def loadFailed(exception: FriendlyException): Unit = {
    println(exception.getMessage) // TODO: use logger
    channel.sendMessage("Failed to load").queue()
  }

}
