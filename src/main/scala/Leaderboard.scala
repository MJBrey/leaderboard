import com.mjbre.SortTeams
import zio.Console.{printLine, readLine}
import zio.{Has, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.io.IOException
import scala.io.Source

object Leaderboard extends ZIOAppDefault {
  val EXAMPLE_USER_INPUT: String =
    "Lions 3, Snakes 3 "+"\n"+
      "Tarantulas 1, FC Awesome 0 "+"\n"+
      "Lions 1, FC Awesome 1 "+"\n"+
      "Tarantulas 3, Snakes 1 "+"\n"+
      "Lions 4, Grouches 0"

  val EXAMPLE_FILE_LOCATION: String =
    "C:/Users/Someone/Documents/results.txt"

  def run: ZIO[zio.ZEnv with zio.ZEnv with Has[ZIOAppArgs], IOException, Unit] = {
    for {
      _              <- printLine("Please input file location, usage: <C:/Users/peanut/Documents/results.txt>")
      fileLocation   <- readLine
      bufferedSource = Source.fromFile(fileLocation)
      userInput      = bufferedSource.getLines.toSeq
      _              <- ZIO.succeed(bufferedSource.close)
      sortedTeams    <- ZIO.succeed(SortTeams.processTeams(userInput))
      formatAndPrint <- ZIO.succeed(sortedTeams.map(list => println(s"${list._2}. ${list._1._1}, ${list._1._2} pts")))
    } yield formatAndPrint
  }
}
