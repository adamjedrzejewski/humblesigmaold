package humblesigma

import humblesigma.commands.BotCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EventDispatcher(prompt: String, commands: Map[String, BotCommand]) extends ListenerAdapter {

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {
    handleCommands(event)
  }

  // override def onSlashCommand(event: SlashCommandEvent): Unit = { }

  def handleCommands(event: MessageReceivedEvent): Unit = {
    val rawMessage = event.getMessage.getContentRaw
    if (!rawMessage.startsWith(prompt)) {
      return
    }

    // TODO: get command and arguments
    // command, args = parseCommand(rawMessage)
    val command = rawMessage.substring(2).toLowerCase()
    commands.get(command) match {
      case Some(cmd) => cmd.handle(event)
      case None => ()
    }
  }

}
