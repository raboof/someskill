package someskill

import scala.util.Random

object Engine {
  type Player = String

  case class Game(winner: Player, loser: Player)
}

class Engine {
  import Engine._

  case class Skill(buckets: Array[Double]) {
    lazy val level = buckets.zipWithIndex.map { case (value, idx) => (idx - (numberOfBuckets / 2)) * value }.sum
  }

  private val numberOfBuckets = 100
  private[someskill] val defaultSkill: Skill = Skill(Array.fill(numberOfBuckets)(0.1))
  private var distribution: Map[Player, Skill] = Map.empty.withDefaultValue(defaultSkill)

  def calculateDistributions(winner: Array[Double], loser: Array[Double]) = {
    val newWinner = winner.zipWithIndex.map {
      case (oldValue, idx) => Range(0, idx).map(loser(_) * oldValue).sum
    }
    val newLoser = loser.zipWithIndex.map {
      case (oldValue, idx) => Range(idx, numberOfBuckets - 1).map(winner(_) * oldValue).sum
    }
    // I guess we'll have to normalize the new distributions?
    (newWinner, newLoser)
  }

  def learn(game: Game) = {
    val (newWinnerDistribution, newLoserDistribution) =
      calculateDistributions(distribution(game.winner).buckets, distribution(game.loser).buckets)

    distribution = distribution +
      (game.winner -> Skill(newWinnerDistribution))
      (game.loser -> Skill(newLoserDistribution))
  }

  def predictWinner(one: Player, two: Player): Player =
    if (distribution(one).level > distribution(two).level) one
    else if (distribution(one).level == distribution(two).level && Random.nextBoolean()) one
    else two
}
