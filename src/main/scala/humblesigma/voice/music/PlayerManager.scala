package humblesigma.voice.music

import com.sedmelluq.discord.lavaplayer.player.{AudioPlayerManager, DefaultAudioPlayerManager}
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import net.dv8tion.jda.api.entities.{Guild, TextChannel}

import scala.collection.mutable

object PlayerManager {
  val audioPlayerManager: AudioPlayerManager = new DefaultAudioPlayerManager
  private val musicManagers: mutable.HashMap[Long, GuildMusicManager] = new mutable.HashMap[Long, GuildMusicManager]

  //# constructor
  AudioSourceManagers.registerRemoteSources(audioPlayerManager)
  AudioSourceManagers.registerLocalSource(audioPlayerManager)
  //#

  def loadAndPlay(channel: TextChannel, trackUrl: String): Unit = {
    val musicManager = getMusicManager(channel.getGuild)
    audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new MusicLoadResultHandler(musicManager, channel))
  }

  def skipSong(channel: TextChannel): Unit = {
    val musicManager = getMusicManager(channel.getGuild)
    musicManager.scheduler.skipTrack()
  }

  def getMusicManager(guild: Guild): GuildMusicManager = {
    musicManagers.getOrElseUpdate(guild.getIdLong, {
      val guildManager = new GuildMusicManager(audioPlayerManager.createPlayer())
      guild.getAudioManager.setSendingHandler(guildManager.sendHandler)
      guildManager
    })
  }

  def clearPlayQueue(channel: TextChannel): Unit = {
    val musicManager = getMusicManager(channel.getGuild)
    musicManager.scheduler.clearQueue()
  }

}
