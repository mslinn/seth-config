organization := "com.micronautics"
name := "seth-config"
version := "0.1.0"

sbtPlugin := true

scalaVersion in Global := "2.10.6"

libraryDependencies ++= Seq(
  "com.typesafe"   % "config"      % "1.2.1" withSources()
)
