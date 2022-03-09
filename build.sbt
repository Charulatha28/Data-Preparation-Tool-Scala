name := "dataPrepationTool"

version := "0.1"

scalaVersion := "2.13.8"
lazy val root = (project in file("."))
  .settings(
    name := "dataPreparationTool",
    libraryDependencies += "org.apache.opennlp" % "opennlp-tools" % "1.9.3",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.10",
    libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.12.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test
  )
