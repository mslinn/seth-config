import sbt._
import Keys._

object TestPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    lazy val sethConfig = TaskKey[Unit]("seth-config", "Tests the seth-config autoplugin")
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    Seq(commands += sethConfig),
    organization  := "com.micronautics",
    version       := "0.1.0",
    scalaVersion  := "2.11.11",
    scalacOptions := Seq(
      "-unchecked",
      "-deprecation",
      "-encoding", "utf8",
      "-feature",
      "-language:existentials",
      "-language:implicitConversions"
    )
  )

  // do I need this? If so, is it correct?
  lazy val sethConfig =
    Command.command("sethConfig") { (state: State) =>
      val sethConfig = SethConfig.apply
      println(sethConfig)
      state
    }
}
