import humblesigma.EventDispatcher
import humblesigma.actions._
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.cache.CacheFlag
import net.dv8tion.jda.api.utils.{ChunkingFilter, MemberCachePolicy}

import scala.io.Source
import scala.util.{Failure, Success, Try, Using}


object Main {

  val commands: Map[String, BotAction] = {
    val commands = List(
      new PingAction(),
      new HelpAction(),
      new EchoAction(),
      new PlayAction(),
      new JoinAction(),
      new LeaveAction()
    )

    commands.flatMap { cmd =>
      cmd.names.map { name =>
        (name, cmd)
      }
    }.toMap
  }

  // TODO: config file
  def main(args: Array[String]): Unit = {
    val tokenFile = "token.txt"

    readToken(tokenFile) match {
      case Failure(s) =>
        println(s"Failed to read token file: ${s.getMessage}")
        System.exit(1)

      case Success(token) =>
        val jdaBuilder = getBuilder(token)
        login(jdaBuilder)

    }
  }

  def login(jdaBuilder: JDABuilder): Unit = {
    jdaBuilder.build().awaitReady()
  }

  def getBuilder(token: String): JDABuilder =
    JDABuilder.createDefault(token)
      .disableCache(CacheFlag.ACTIVITY)
      .enableCache(CacheFlag.VOICE_STATE)
      .setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER))
      .setChunkingFilter(ChunkingFilter.NONE)
      .setBulkDeleteSplittingEnabled(false)
      .setActivity(Activity.listening("Nightshift TV - D r i v e F o r e v e r"))
      .addEventListeners(new EventDispatcher("::", commands))

  def readToken(tokenFile: String): Try[String] = Using(Source.fromFile(tokenFile)) { source => source.getLines().mkString }

}
