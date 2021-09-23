import net.dv8tion.jda.api.JDABuilder

import scala.io.Source
import scala.util.{Failure, Success, Try, Using}


object Main {

  def main(args: Array[String]): Unit = {
    val tokenFile = "token.txt"
    readToken(tokenFile) match {
      case Failure(s) =>
        println(s"Failed to read token file: ${s.getMessage}")
        System.exit(1)
      case Success(token) =>
        val jdaBuilder = getBuilder(token)
        jdaBuilder.build().awaitReady()
        println(token)
    }
  }

  def getBuilder(token: String): JDABuilder = {
    import net.dv8tion.jda.api.entities.Activity
    import net.dv8tion.jda.api.utils.cache.CacheFlag
    import net.dv8tion.jda.api.utils.{ChunkingFilter, MemberCachePolicy}

    val builder = JDABuilder.createDefault(token)

    builder.disableCache(CacheFlag.ACTIVITY)
    builder.setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER))
    builder.setChunkingFilter(ChunkingFilter.NONE)
    builder.setBulkDeleteSplittingEnabled(false)
    builder.setActivity(Activity.listening("Nightshift TV - D r i v e F o r e v e r"))

    builder
  }

  def readToken(tokenFile: String): Try[String] = Using(Source.fromFile(tokenFile)) { source => source.getLines().mkString }
}
