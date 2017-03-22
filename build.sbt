import Dependencies._

val akkaVersion = "2.4.17"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.seaBE",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "supermarket",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.1",
      "com.typesafe.akka" % "akka-slf4j_2.12" % akkaVersion)
)
