import sbt._
import sbt.Keys._

object GulagahaBuild extends Build {

  lazy val gulagaha = Project(
    id = "gulagaha",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "Gulagaha",
      organization := "com.standonopenstds",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.3",
      scalacOptions ++= Seq("-feature", "-deprecation"),
      resolvers += "Typesafe Releases" at "https://oss.sonatype.org/content/repositories/releases",
      libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-actor" % "2.2.3",
          "com.typesafe.akka" %% "akka-testkit" % "2.2.3",
           "org.scalatest" %% "scalatest" % "2.0.RC3" % "test")
      )
  )
}
