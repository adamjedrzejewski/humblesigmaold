package humblesigma.voice.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer

class GuildMusicManager(val audioPlayer: AudioPlayer) {
  val scheduler = new TrackScheduler(audioPlayer)
  val sendHandler = new MusicPlayerSendHandler(audioPlayer)
  audioPlayer.addListener(scheduler)


}
