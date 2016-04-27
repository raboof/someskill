package someskill

import scala.util.Random
import scala.math._

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
  private def normalDistributionDensity(x: Int, mu: Double = numberOfBuckets/2, sigma: Double = numberOfBuckets/4) =
    1.0 / (sigma * sqrt(2.0 * Pi)) * pow(E, -pow(x - mu, 2) / (2 * pow(sigma, 2)))

  private[someskill] val defaultSkill: Skill = Skill(normalize(Range(0, numberOfBuckets).map(normalDistributionDensity(_)).toArray))
  // private[someskill] val defaultSkill: Skill = Skill(normalize(Array.fill(numberOfBuckets)(0.01)))
  private var distribution: Map[Player, Skill] = Map.empty.withDefaultValue(defaultSkill)

  def normalize(distribution: Array[Double]) = {
    distribution.map(_ / distribution.sum)
  }

  def calculateDistributions(winner: Array[Double], loser: Array[Double]) = {
    val newWinner = winner.zipWithIndex.map {
      case (oldValue, idx) => Range(0, idx + 1).map(loser(_) * oldValue).sum
    }
    val newLoser = loser.zipWithIndex.map {
      case (oldValue, idx) => Range(idx, numberOfBuckets).map(winner(_) * oldValue).sum
    }
    (normalize(newWinner), normalize(newLoser))
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
