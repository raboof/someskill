package someskill

import org.scalatest._

class TestDistributionUpdate extends WordSpec with Matchers {
  val engine = new someskill.Engine()
  import engine._

  "The distribution update function" should {

    def asString(distribution: Array[Double]) =
      distribution.map(d => (d * 10000).toInt).mkString(", ")

    "update distributions correctly when a match is won" in {
      val (winner, loser) = calculateDistributions(defaultSkill.buckets, defaultSkill.buckets)

      println(s"New winner distribution: ${asString(winner)}")
      println(s"New loser distribution: ${asString(loser)}")
      Skill(winner).level should be > Skill(loser).level
      Skill(winner).level should be > defaultSkill.level
      Skill(loser).level should be < defaultSkill.level
    }

    "losing from a loser should be heavier than losing from a normal player" in {
      val (_, loseFromDefault) = calculateDistributions(defaultSkill.buckets, defaultSkill.buckets)
      val (_, loseFromLoser) = calculateDistributions(loseFromDefault, defaultSkill.buckets)

      Skill(loseFromLoser).level should be < Skill(loseFromDefault).level
    }
  }
}
