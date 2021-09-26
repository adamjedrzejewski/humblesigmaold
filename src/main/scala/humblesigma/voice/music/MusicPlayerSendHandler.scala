package humblesigma.voice.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame
import net.dv8tion.jda.api.audio.AudioSendHandler

import java.nio.ByteBuffer

class MusicPlayerSendHandler(audioPlayer: AudioPlayer) extends AudioSendHandler {

  private var lastFrame: AudioFrame = _

  override def canProvide: Boolean = {
    lastFrame = audioPlayer.provide
    lastFrame != null
  }

  override def provide20MsAudio(): ByteBuffer = ByteBuffer.wrap(lastFrame.getData)

  override def isOpus = true // lavaplayer gives you opus anyway

}
