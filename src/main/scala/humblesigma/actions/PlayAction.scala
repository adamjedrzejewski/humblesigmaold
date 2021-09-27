package humblesigma.actions

import humblesigma.Command
import humblesigma.voice.VoiceUtility
import humblesigma.voice.music.PlayerManager
import net.dv8tion.jda.api.entities.{Member, TextChannel}
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

import java.net.{URI, URISyntaxException}

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
      case Some(query) =>
        if (!selfVoiceState.inVoiceChannel()) {
          VoiceUtility.joinChannel(guild, voiceChannel, event.getChannel)
        }

        playMusic(textChannel, query)

      case None =>
        textChannel.sendMessage("Provide a query to search.").queue()
    }
  }

  def isUrl(query: String): Boolean = {
    try {
      new URI(query)
      true
    } catch {
      case _: URISyntaxException => false
    }

  }

  private def playMusic(channel: TextChannel, query: String):Unit = {
    val link = if (isUrl(query)) {
      query
    } else {
      s"ytsearch:$query"
    }

    PlayerManager.loadAndPlay(channel, link)
  }


}
