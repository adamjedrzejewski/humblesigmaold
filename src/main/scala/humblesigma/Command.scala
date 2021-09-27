package humblesigma

trait Command {
  val names: List[String]
  val helpMessage: String
}
