import com.mjbre.SortTeams
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import com.mjbre.model.shorthands.{ranking, score, teamName, teamTuple, totalPoints}

class SortTeamsSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {
  "SortTeams" should {
    "rankTeams" in {
      val inputTuples: List[(teamName, totalPoints)] =
        List(("Tarantulas", 6), ("Lions", 5), ("FC Awesome", 1), ("Snakes", 1), ("Grouches", 0))
      val expectedValue: List[((teamName, totalPoints), ranking)] =
        List((("Tarantulas", 6), 1),(("Lions", 5), 2),(("FC Awesome", 1), 3),(("Snakes", 1), 3),(("Grouches", 0), 4))

      val actualValue: List[((teamName, totalPoints), ranking)] = SortTeams.rankTeams(inputTuples)

      assert(actualValue.equals(expectedValue))
      assert(true)
    }

    "teamPoints return one point for each team in draw" in {
      val inputTuple: teamTuple = (("Lions", 3), ("Snakes", 3))
      val expectedValue: teamTuple = (("Lions", 1), ("Snakes", 1))
      val actualValue: teamTuple = SortTeams.teamPoints(inputTuple)

      assert(actualValue.equals(expectedValue))
    }


    "teamPoints return win and loss points" in {
      val inputTuple: teamTuple = (("Lions", 3), ("Snakes", 1))
      val expectedValue: teamTuple = (("Lions", 3), ("Snakes", 0))
      val actualValue: teamTuple = SortTeams.teamPoints(inputTuple)

      assert(actualValue.equals(expectedValue))
    }

    "transform string to teamTuple with team names and scores" in {
      val inputString: String  = "Lions 3, Snakes 3"
      val expectedValue: teamTuple = (("Lions", 3), ("Snakes", 3))
      val actualValue: teamTuple = SortTeams.transform(inputString)

      assert(actualValue.equals(expectedValue))
    }

    "processTeams" in {
      val inputStrings: Seq[String] =
        Seq("Lions 3, Snakes 3 ", "Tarantulas 1, FC Awesome 0 ", "Lions 1, FC Awesome 1 ", "Tarantulas 3, Snakes 1 ", "Lions 4, Grouches 0")
      val expectedValue: List[((teamName, totalPoints), ranking)] =
        List((("Tarantulas", 6), 1),(("Lions", 5), 2),(("FC Awesome", 1), 3),(("Snakes", 1), 3),(("Grouches", 0), 4))
      val actualValue: List[((teamName, totalPoints), ranking)] = SortTeams.processTeams(inputStrings)

      assert(actualValue.equals(expectedValue))
    }
  }
}
