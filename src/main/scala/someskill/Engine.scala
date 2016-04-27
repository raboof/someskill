package someskill

object Engine {
  type Player = String

  case class Game(winner: Player, loser: Player)
}

class Engine {
  import Engine._

  case class Skill(buckets: Array[Double])

  private val defaultSkill: Skill = Skill(Array.fill(100)(0.0))
  private var distributions: Map[Player, Skill] = Map.empty.withDefaultValue(defaultSkill)

  def learn(game: Game) = {
    // TODO implement actually learning :)
  }

  def predictWinner(one: Player, two: Player): Player =
    // TODO implement actually predicting based learned skill
    one
}
