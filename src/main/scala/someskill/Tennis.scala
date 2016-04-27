package someskill

import scala.io._

object Tennis extends App {
  val engine = new Engine()

  val games = Source.fromURL(Tennis.getClass.getResource("/atp/atp_matches_1968.csv"))(Codec.UTF8)
    .getLines()
    .drop(1)
    .map(line => {
      val columns = line.split(",")
      Engine.Game(winner = columns(10), loser = columns(20))
    }).toList

    games.foreach(engine.learn(_))

    val correctPredictions = games.count {
      game => engine.predictWinner(game.winner, game.loser) == game.winner
    }
    println(s"Correctly predicted $correctPredictions out of ${games.length}, which is ${100 * correctPredictions / games.length}%")
}
