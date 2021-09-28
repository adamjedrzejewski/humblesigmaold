package humblesigma.actions

import humblesigma.Command
import humblesigma.voice.VoiceUtility
import humblesigma.voice.music.PlayerManager
import net.dv8tion.jda.api.entities.{Member, TextChannel}
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

import java.net.{URI, URISyntaxException}

class PlayAction extends Action with Command {
  override val names = List("play", "p")
  override val helpMessage: String = "play music"

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val textChannel = event.getChannel
    val member: Member = event.getMember
    val guild = event.getGuild
    val voiceState = member.getVoiceState
    val voiceChannel = voiceState.getChannel
    val selfVoiceState = guild.getSelfMember.getVoiceState

    args match {
      case Some(query) =>
        val joined = VoiceUtility.joinChannel(guild, voiceChannel, event.getChannel)
        if (joined) {
          playMusic(textChannel, query)
        }

      case None =>
        textChannel.sendMessage("Provide a query to search.").queue()
    }
  }

  private def playMusic(channel: TextChannel, query: String): Unit = {
    val link = if (isUrl(query)) {
      query
    } else {
      s"ytsearch:$query"
    }

    PlayerManager.loadAndPlay(channel, link)
  }

  def isUrl(query: String): Boolean = {
    try {
      new URI(query)
      true
    } catch {
      case _: URISyntaxException => false
    }
  }
}
