organization := "com.micronautics"
name := "seth-config"
version := "0.1.0-SNAPSHOT"

sbtPlugin := true

scalaVersion in Global := "2.10.6"

libraryDependencies ++= Seq(
  "com.typesafe"   %  "config"     % "1.2.1" withSources()
)

addSbtPlugin("com.scalapenos" % "sbt-prompt" % "1.0.2")
