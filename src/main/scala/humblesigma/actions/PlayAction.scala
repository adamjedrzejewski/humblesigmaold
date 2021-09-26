package humblesigma.actions

import humblesigma.Command
import humblesigma.voice.VoiceUtility
import humblesigma.voice.music.PlayerManager
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class PlayAction extends Action with Command {
  override val names = List("play", "p")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val textChannel = event.getChannel
    val member: Member = event.getMember
    val guild = event.getGuild
    val voiceState = member.getVoiceState
    val voiceChannel = voiceState.getChannel
    val selfVoiceState = guild.getSelfMember.getVoiceState

    args match {
      case Some(url) =>
        VoiceUtility.joinChannel (guild, voiceChannel, event.getChannel)
        PlayerManager.loadAndPlay (textChannel, url)
      case None => ()
    }
  }


}
