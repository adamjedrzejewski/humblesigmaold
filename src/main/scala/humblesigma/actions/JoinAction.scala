package humblesigma.actions

import humblesigma.Command
import humblesigma.voice.VoiceUtility
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class JoinAction extends Action with Command {
  override val names: List[String] = List("join", "j")
  override val helpMessage: String = "join voice channel"

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val member = event.getMember
    val voiceState = member.getVoiceState
    val voiceChannel = voiceState.getChannel

    VoiceUtility.joinChannel(member.getGuild, voiceChannel, event.getChannel, verbose = true)
  }
}
