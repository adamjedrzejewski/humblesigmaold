package humblesigma.actions

import humblesigma.Command
import humblesigma.voice.VoiceUtility
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class LeaveAction extends Action with Command {

  override val names: List[String] = List("leave")
  override val helpMessage: String = "leave voice channel"

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit
  = VoiceUtility.leaveChannel(event.getMember.getGuild, event.getChannel, verbose = true)
}
