package com.mjbre

import com.mjbre.model.shorthands.{ranking, score, teamName, teamTuple, totalPoints}

object SortTeams {
  def rankTeams(inputSettlements: List[(teamName, totalPoints)]): List[((teamName, totalPoints), ranking)] = {
    inputSettlements.toSeq
      //Sort the new tuple alphabetically and then by total points.
      .sortBy(_._1).sortBy(_._2)(Ordering[totalPoints].reverse)
      //Then for each tuple of teamName, totalPoints fold left with an empty list, and two starter int variables that will be used to rank each tuple
      .foldLeft((List[((teamName, totalPoints), Int)](), -1, -1)){
        case ((rankedList, initialPoints, cumulativeRank),(teamName,totalPoints)) =>
          if (initialPoints==totalPoints)
            (((teamName,totalPoints), cumulativeRank+1)::rankedList, totalPoints, cumulativeRank)
          else
            (((teamName,totalPoints),cumulativeRank+2)::rankedList, totalPoints, cumulativeRank+1)
      }._1.reverse
  }

  def teamPoints(teams: teamTuple): teamTuple = {
    val teamOneName  = teams._1._1
    val teamOneScore = teams._1._2
    val teamTwoName  = teams._2._1
    val teamTwoScore = teams._2._2

    if (teamOneScore > teamTwoScore) {
      ((teamOneName, 3), (teamTwoName, 0))
    } else if (teamOneScore < teamTwoScore) {
     ((teamOneName, 0), (teamTwoName, 3))
    } else {
      ((teamOneName, 1), (teamTwoName, 1))
    }
  }

  def transform(gameString: String): teamTuple = {
    val t = gameString.split(",").map {
        team =>
          val teamNameRegex: String = "\\d"
          val teamScoreRegex: String = "\\D"
          //trim left white spaces, trim right white spaces
          val tempString = team.replaceAll("\\s+$", "").replaceAll("^\\s+", "")
          val score = tempString.replaceAll(teamScoreRegex,"").toInt
          val teamName = tempString.replaceAll(teamNameRegex, "").trim
          (teamName, score)
    }

    /**
    * Only line I worry about because of accessing a collection with an explicit index isn't always safe.
    * I assume it's fine here because I have been guaranteed that the input won't need validation.
    */
    ((t(0)._1, t(0)._2), (t(1)._1, t(1)._2))
  }

  def processTeams(games: Seq[String]): List[((teamName, totalPoints), ranking)] = {
    val unsortedLeaderBoard
      = games.foldLeft((Map[teamName, totalPoints](), games)) {
        case (teamsPointsMap, game) =>
          val tp = teamPoints(transform(game))

          val teamOneName   = tp._1._1
          val teamOnePoints = tp._1._2
          val teamTwoName   = tp._2._1
          val teamTwoPoints = tp._2._2

          val firstUpdatedMap: Map[teamName, totalPoints] = teamsPointsMap._1.get(teamOneName) match {
            case Some(currentPoints) =>
              teamsPointsMap._1.map(currentTeam => if (currentTeam._1 == teamOneName) (teamOneName, teamOnePoints + currentPoints) else currentTeam)
            case None =>
              teamsPointsMap._1 ++ Map(teamOneName -> teamOnePoints)
          }

          val SecondUpdatedMap: Map[teamName, totalPoints] = firstUpdatedMap.get(teamTwoName) match {
            case Some(currentPoints) =>
              firstUpdatedMap.map(currentTeam => if (currentTeam._1 == teamTwoName) (teamTwoName, teamTwoPoints + currentPoints) else currentTeam)
            case None =>
              firstUpdatedMap ++ Map(teamTwoName -> teamTwoPoints)
          }

          (SecondUpdatedMap, games)
      }._1.toList

    rankTeams(unsortedLeaderBoard)
  }
}
