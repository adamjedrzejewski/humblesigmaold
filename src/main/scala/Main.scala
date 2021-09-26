import humblesigma.actions._
import humblesigma.{CommandHandler, Configuration}
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.cache.CacheFlag
import net.dv8tion.jda.api.utils.{ChunkingFilter, MemberCachePolicy}
import org.json4s._
import org.json4s.native.Serialization.read

import scala.io.Source
import scala.util.{Failure, Success, Using}


object Main {

  private val configFile = "config.json"

  def main(args: Array[String]): Unit = {
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

  def commands(configuration: Configuration): Map[String, Action] = {
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

  private def readConfig(configFile: String) = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    Using(Source.fromFile(configFile)) { source =>
      read[Configuration](source.getLines().mkString)
    }
  }

  def login(jdaBuilder: JDABuilder): Unit = {
    jdaBuilder.build().awaitReady()
  }

  def getBuilder(configuration: Configuration, commands: Map[String, Action]): JDABuilder = {
    JDABuilder.createDefault(configuration.token)
      .disableCache(CacheFlag.ACTIVITY)
      .enableCache(CacheFlag.VOICE_STATE)
      .setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER))
      .setChunkingFilter(ChunkingFilter.NONE)
      .setBulkDeleteSplittingEnabled(false)
      .setActivity(Activity.listening("Nightshift TV - D r i v e F o r e v e r"))
      .addEventListeners(new CommandHandler(configuration.prompt, commands))
  }

}
