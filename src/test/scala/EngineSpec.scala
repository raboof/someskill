package someskill

import org.scalatest._

class EngineSpec extends WordSpec with Matchers {
  "The engine" should {
    "correctly use the trained distributions" in {
      val engine = new Engine()

      engine.learn(Engine.Game("winner", "loser"))
      engine.predictWinner("winner", "loser") should be("winner")
    }
  }
}
