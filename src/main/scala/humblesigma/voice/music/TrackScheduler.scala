package humblesigma.voice.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.{AudioTrack, AudioTrackEndReason}

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

class TrackScheduler(audioPlayer: AudioPlayer) extends AudioEventAdapter {

  private val queue: BlockingQueue[AudioTrack] = new LinkedBlockingQueue[AudioTrack]

  def addTrackToQueue(track: AudioTrack): Unit = {
    if (!audioPlayer.startTrack(track, true)) {
      queue.offer(track)
    }
  }

  override def onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason): Unit = {
    if (endReason.mayStartNext) {
      nextTrack()
    }
  }

  def nextTrack(): Boolean = audioPlayer.startTrack(queue.poll(), false)

}
