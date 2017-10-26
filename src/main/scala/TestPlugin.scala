import TestPlugin.allRequirements
import sbt.{AutoPlugin, TaskKey}

object TestPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    lazy val sethConfig = TaskKey[Unit]("seth-config", "Tests the seth-config autoplugin")
  }

  import autoImport._

    override lazy val projectSettings = Seq(
      sethConfig := {
        val sethConfig = SethConfig.apply
        println(sethConfig)
      }
    )
}
