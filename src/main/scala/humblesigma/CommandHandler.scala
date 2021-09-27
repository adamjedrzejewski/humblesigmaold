package humblesigma

import humblesigma.actions.Action
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandHandler(prompt: String, commands: Map[String, Action with Command]) extends ListenerAdapter {

  override def onGuildMessageReceived(event: GuildMessageReceivedEvent): Unit = {
    handleCommands(event)
  }

  // TODO: add slash commands
  // override def onSlashCommand(event: SlashCommandEvent): Unit = { }

  def handleCommands(event: GuildMessageReceivedEvent): Unit = {
    val messageAuthor = event.getAuthor
    val botUser = event.getJDA.getSelfUser

    // ignore yourself and bots
    if (botUser.equals(messageAuthor) || messageAuthor.isSystem || messageAuthor.isBot) {
      return
    }

    val rawMessage = event.getMessage.getContentRaw
    if (!rawMessage.startsWith(prompt)) {
      return
    }

    val rawCommand = rawMessage.substring(prompt.length)
    val (command, args) = parseCommand(rawCommand)

    if (command == "help") {
      printHelp(event.getChannel)
      return
    }

    commands.get(command.toLowerCase()) match {
      case Some(cmd) => cmd.handle(event, command, args)
      case None => ()
    }
  }

  def printHelp(channel: TextChannel): Unit = {
    val action = channel.sendMessage("Help: \n")
    action.append(s"My prompt is `$prompt`")
    action.append("\n")
    action.append("\n")
    action.append("Commands:\n")

    commands.values.toSet[Command].foreach { command =>
      action.append(s"${command.helpMessage} \n")
    }

    action.queue()
  }

  def parseCommand(rawCommand: String): (String, Option[String]) = {
    val elems = rawCommand.split(raw"\s+", 2)
    val command = elems(0)
    val args = elems.size match {
      case 2 => Some(elems(1))
      case _ => None
    }
    (command, args)
  }

}
