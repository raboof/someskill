package someskill

import org.scalatest._

class TestDistributionUpdate extends WordSpec with Matchers {
  "The distribution update function" should {
    "update distributions correctly when a match is won" in {
      val engine = new someskill.Engine()
      import engine._

      val (winner, loser) = calculateDistributions(defaultSkill.buckets, defaultSkill.buckets)
      println(s"New winner distribution: ${winner.mkString(",")}")
      println(s"New loser distribution: ${loser.mkString(",")}")
      Skill(winner).level should be > Skill(loser).level
      Skill(winner).level should be > defaultSkill.level
      Skill(loser).level should be < defaultSkill.level
    }
  }
}
