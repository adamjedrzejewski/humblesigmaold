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

    val rawCommand = rawMessage.substring(2)
    val (command, args) = parseCommand(rawCommand)
    commands.get(command.toLowerCase()) match {
      case Some(cmd) => cmd.handle(event, command, args)
      case None => ()
    }
  }

  def parseCommand(rawCommand: String): (String, Option[String]) = {
    val elems = rawCommand.split(" ", 2)
    val command = elems(0)
    val args = elems.size match {
      case 2 => Some(elems(1))
      case _ => None
    }
    (command, args)
  }

}
