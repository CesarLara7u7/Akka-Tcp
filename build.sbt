ThisBuild / organization := "com.cesar"

val AkkaVersion = "2.8.5"
val AkkaHttpVersion = "10.5.3"


Compile / mainClass := Some("com.cesar.akka.tcp.MainApp")

Compile / discoveredMainClasses := Seq()

lazy val commonSettings = Seq(
  scalaVersion := "2.13.12",
)

lazy val root = (project in file("."))
  .settings(
    name := "akka-tcp-app",
    commonSettings,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
      "ch.qos.logback" % "logback-classic" % "1.4.14",
      "com.beachape" %% "enumeratum" % "1.7.3",
      "com.beachape" %% "enumeratum-reactivemongo-bson" % "1.7.3",
    )
  )
