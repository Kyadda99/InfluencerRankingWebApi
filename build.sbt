ThisBuild / scalaVersion := "2.13.16"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "RankingInfluencer",
    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play" % "2.8.8", // Play framework
      "com.typesafe.play" %% "play-slick" % "5.2.0", // Play-Slick integration
      "com.typesafe.play" %% "play-slick-evolutions" % "5.2.0", // Play Evolutions for database migrations
      "com.zaxxer" % "HikariCP" % "5.0.1", // HikariCP connection pool
      "com.typesafe.slick" %% "slick" % "3.4.1", // Slick core
      "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1", // Slick HikariCP integration
      "org.postgresql" % "postgresql" % "42.2.23"

    )
  )