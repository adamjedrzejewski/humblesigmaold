package humblesigma.voice

import net.dv8tion.jda.api.entities.{Guild, Member, TextChannel, VoiceChannel}

object VoiceUtility {

  def joinChannel(guild: Guild, voiceChannel: VoiceChannel, textChannel: TextChannel): Boolean = {
    val selfVoiceState = guild.getSelfMember.getVoiceState

//    if (selfVoiceState.inVoiceChannel()) {
//      textChannel.sendMessage(s"I'm already in ${selfVoiceState.getChannel.getName} channel").queue()
//    } else
    if (voiceChannel != null) {
      connectTo(voiceChannel)
      textChannel.sendMessage(s"Connected to ${voiceChannel.getName} channel").queue()
      true
    } else {
      textChannel.sendMessage(s"You aren't connected to any channel").queue()
      false
    }
  }

  private def connectTo(voiceChannel: VoiceChannel): Unit = {
    val guild = voiceChannel.getGuild
    val audioManager = guild.getAudioManager

    audioManager.openAudioConnection(voiceChannel)
  }

  def leaveChannel(guild: Guild, textChannel: TextChannel): Unit = {
    val selfMember = guild.getSelfMember
    val selfVoiceState = selfMember.getVoiceState

    if (selfVoiceState.inVoiceChannel()) {
      guild.getAudioManager.closeAudioConnection()
      textChannel.sendMessage(s"Leaving channel ${selfVoiceState.getChannel.getName}").queue()
    } else {
      textChannel.sendMessage(s"I'm not connected to any channel").queue()
    }
  }

  def isInChannel(member: Member): Boolean = member.getVoiceState.inVoiceChannel()

}
