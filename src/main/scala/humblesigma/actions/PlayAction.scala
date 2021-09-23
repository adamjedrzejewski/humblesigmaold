package humblesigma.actions

import humblesigma.utility.VoiceUtility
import net.dv8tion.jda.api.audio.{AudioReceiveHandler, AudioSendHandler, CombinedAudio}
import net.dv8tion.jda.api.entities.{Member, VoiceChannel}
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

import java.nio.ByteBuffer
import java.util.concurrent.ConcurrentLinkedQueue

class PlayAction extends BotAction {
  override val names = List("play", "p")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val member: Member = event.getMember
    val voiceState = member.getVoiceState
    val voiceChannel = voiceState.getChannel

    VoiceUtility.joinChannel(member.getGuild, voiceChannel, event.getChannel)
  }

  private def playMusic(): Unit = {

  }

  private def connectTo(voiceChannel: VoiceChannel): Unit = {
    val guild = voiceChannel.getGuild
    val audioManager = guild.getAudioManager

    val handler = new EchoHandler

    audioManager.setSendingHandler(handler)
    audioManager.setReceivingHandler(handler)
    audioManager.openAudioConnection(voiceChannel)
  }

}

class EchoHandler extends AudioSendHandler with AudioReceiveHandler {

  private val queue = new ConcurrentLinkedQueue[Array[Byte]]

  override def canProvide: Boolean = !queue.isEmpty

  override def provide20MsAudio(): ByteBuffer = {
    import java.nio.ByteBuffer
    val data = queue.poll
    if (data == null) null
    else ByteBuffer.wrap(data)
  }

  override def canReceiveCombined: Boolean = queue.size < 10

  override def handleCombinedAudio(combinedAudio: CombinedAudio): Unit = {
    if (combinedAudio.getUsers.isEmpty) return
    val data = combinedAudio.getAudioData(1.0f)
    queue.add(data)
  }

  override def isOpus: Boolean = false
}
