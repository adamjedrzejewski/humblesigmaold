package humblesigma.commands

import net.dv8tion.jda.api.audio.{AudioReceiveHandler, AudioSendHandler, CombinedAudio}
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

import java.nio.ByteBuffer
import java.util.concurrent.ConcurrentLinkedQueue

class PlayCommand extends BotCommand {
  override val command: String = "play"

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    command match {
      case "play" => play(event, args)
      case "leave" => leave(event)
    }
  }

  private def play(event: GuildMessageReceivedEvent, args: Option[String]): Unit = {
    val member = event.getMember
    val selfMember = event.getGuild.getSelfMember
    val voiceState = member.getVoiceState
    val voiceChannel = voiceState.getChannel
    val textChannel = event.getChannel

    if (selfMember.getVoiceState.inVoiceChannel()) {
      textChannel.sendMessage(s"I'm already in ${selfMember.getVoiceState.getChannel.getName} channel").queue()
      return
    }

    if (voiceChannel != null) {
      connectTo(voiceChannel)
      textChannel.sendMessage(s"Connected to ${voiceChannel.getName} channel").queue()
    } else {
      textChannel.sendMessage(s"You aren't connected to any channel").queue()
    }
  }

  private def connectTo(voiceChannel: VoiceChannel): Unit = {
    val guild = voiceChannel.getGuild
    val audioManager = guild.getAudioManager

    val handler = new EchoHandler

    audioManager.setSendingHandler(handler)
    audioManager.setReceivingHandler(handler)
    audioManager.openAudioConnection(voiceChannel)
  }

  private def leave(event: GuildMessageReceivedEvent): Unit = {
    val member = event.getGuild.getSelfMember
    val textChannel = event.getChannel
    val voiceState = member.getVoiceState

    if (voiceState.inVoiceChannel) {
      member.getGuild.getAudioManager.closeAudioConnection()
      textChannel.sendMessage(s"Leaving channel ${voiceState.getChannel.getName}").queue()
    } else {
      textChannel.sendMessage(s"I'm not connected to any channel").queue()
    }
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


  override def handleCombinedAudio(combinedAudio: CombinedAudio): Unit = { // we only want to send data when a user actually sent something, otherwise we would just send silence
    if (combinedAudio.getUsers.isEmpty) return
    val data = combinedAudio.getAudioData(1.0f) // volume at 100% = 1.0 (50% = 0.5 / 55% = 0.55)
    queue.add(data)
  }

  override def isOpus: Boolean = false
}
