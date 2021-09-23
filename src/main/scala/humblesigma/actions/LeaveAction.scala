package humblesigma.actions

import humblesigma.utility.VoiceUtility
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class LeaveAction extends BotAction {

  override val names: List[String] = List("leave")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit
    = VoiceUtility.leaveChannel(event.getMember.getGuild, event.getChannel)

}
