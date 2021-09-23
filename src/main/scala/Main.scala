import net.dv8tion.jda.api.JDABuilder
import scala.io.Source

object Main {

  def main(args: Array[String]): Unit = {
    val token = readToken()
    val jda = JDABuilder.createDefault(token).build
  }

  def readToken() = Source.fromFile("token.txt").getLines.toList.mkString
}
