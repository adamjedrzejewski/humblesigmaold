import Main.commands
import humblesigma.{Configuration, EventDispatcher}
import humblesigma.actions._
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.cache.CacheFlag
import net.dv8tion.jda.api.utils.{ChunkingFilter, MemberCachePolicy}
import org.json4s._
import org.json4s.native.Serialization.read

import scala.io.Source
import scala.util.{Failure, Success, Try, Using}


object Main {

  def commands(configuration: Configuration): Map[String, BotAction] = {
    val commands = List(
      new PingAction(),
      new HelpAction(),
      new EchoAction(),
      new PlayAction(),
      new JoinAction(),
      new LeaveAction(),
      new ShutdownAction(configuration)
    )

    commands.flatMap { cmd =>
      cmd.names.map { name =>
        (name, cmd)
      }
    }.toMap
  }

  def main(args: Array[String]): Unit = {
    val configFile = "config.json"

    readConfig(configFile) match {
      case Failure(s) =>
        println(s"Failed to read config file: ${s.getMessage}")
        System.exit(1)

      case Success(configuration) =>
        val cmds = commands(configuration)
        val jdaBuilder = getBuilder(configuration, cmds)
        login(jdaBuilder)
    }

  }

  private def readConfig(configFile: String) = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    Using(Source.fromFile(configFile)) { source =>
      read[Configuration](source.getLines().mkString)
    }
  }

  def login(jdaBuilder: JDABuilder): Unit = {
    jdaBuilder.build().awaitReady()
  }

  def getBuilder(configuration: Configuration, commands: Map[String, BotAction]): JDABuilder = {
    JDABuilder.createDefault(configuration.token)
      .disableCache(CacheFlag.ACTIVITY)
      .enableCache(CacheFlag.VOICE_STATE)
      .setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER))
      .setChunkingFilter(ChunkingFilter.NONE)
      .setBulkDeleteSplittingEnabled(false)
      .setActivity(Activity.listening("Nightshift TV - D r i v e F o r e v e r"))
      .addEventListeners(new EventDispatcher(configuration.prompt, commands))
  }

  def readToken(tokenFile: String): Try[String] = Using(Source.fromFile(tokenFile)) { source => source.getLines().mkString }

}
