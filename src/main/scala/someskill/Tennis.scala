package someskill

import scala.io._

object Tennis extends App {
  val engine = new Engine()

  Source.fromURL(Tennis.getClass.getResource("/atp/atp_matches_1968.csv"))(Codec.UTF8)
    .getLines()
    .drop(1)
    .map(line => {
      val columns = line.split(",")
      Engine.Game(winner = columns(10), loser = columns(20))
    })
    .foreach(engine.learn(_))
}
