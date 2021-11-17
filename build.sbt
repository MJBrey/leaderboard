name := "league"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies += "dev.zio"       %% "zio" % "2.0.0-M3"
libraryDependencies += "dev.zio"       %% "zio-streams" % "2.0.0-M3"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % Test

mainClass in (Compile, run) := Some("Leaderboard")