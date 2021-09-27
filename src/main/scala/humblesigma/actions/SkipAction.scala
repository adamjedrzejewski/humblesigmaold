package humblesigma.actions
import humblesigma.Command
import humblesigma.voice.VoiceUtility
import humblesigma.voice.music.PlayerManager
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class SkipAction extends Action with Command {
  override val names: List[String] = List("skip", "s")

  override def handle(event: GuildMessageReceivedEvent, command: String, args: Option[String]): Unit = {
    val textChannel = event.getChannel
    val inChannel = VoiceUtility.isInChannel(event.getGuild.getSelfMember)

    if (!inChannel) {
      textChannel.sendMessage(s"I'm not in any channel").queue()
    } else {
      PlayerManager.skipSong(textChannel)
    }
  }

  override val helpMessage: String = "skip currently playing song"
}
