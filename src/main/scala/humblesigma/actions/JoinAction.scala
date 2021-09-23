package humblesigma.actions
import humblesigma.utility.VoiceUtility
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class JoinAction extends BotAction {
  override val names: List[String] = List("join")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val member = event.getMember
    val voiceState = member.getVoiceState
    val voiceChannel = voiceState.getChannel

    VoiceUtility.joinChannel(member.getGuild, voiceChannel, event.getChannel)
  }

}