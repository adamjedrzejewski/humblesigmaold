package humblesigma.voice.music

import com.sedmelluq.discord.lavaplayer.player.{AudioLoadResultHandler, AudioPlayerManager, DefaultAudioPlayerManager}
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.{AudioPlaylist, AudioTrack}
import net.dv8tion.jda.api.entities.{Guild, TextChannel}

import scala.collection.mutable

object PlayerManager {
  val audioPlayerManager: AudioPlayerManager = new DefaultAudioPlayerManager
  private val musicManagers: mutable.HashMap[Long, GuildMusicManager] = new mutable.HashMap[Long, GuildMusicManager]

  AudioSourceManagers.registerRemoteSources(audioPlayerManager)
  AudioSourceManagers.registerLocalSource(audioPlayerManager)

  def loadAndPlay(channel: TextChannel, trackUrl: String): Unit = {
    val musicManager = getMusicManager(channel.getGuild)
    audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler {
      override def trackLoaded(track: AudioTrack): Unit = {
        musicManager.scheduler.addTrackToQueue(track)
        channel.sendMessage("Adding to queue ").append(track.getInfo.title).queue()
      }

      override def playlistLoaded(playlist: AudioPlaylist): Unit = {}

      override def noMatches(): Unit = {}

      override def loadFailed(exception: FriendlyException): Unit = {}
    })
  }

  def getMusicManager(guild: Guild): GuildMusicManager = {
    musicManagers.getOrElseUpdate(guild.getIdLong, {
      val guildManager = new GuildMusicManager(audioPlayerManager.createPlayer())
      guild.getAudioManager.setSendingHandler(guildManager.sendHandler)
      guildManager
    })
  }

}
