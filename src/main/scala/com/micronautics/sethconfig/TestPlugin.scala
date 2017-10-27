package com.micronautics.sethconfig

import sbt._
import Keys._

import com.scalapenos.sbt.prompt._
import SbtPrompt.autoImport._

object TestPlugin extends AutoPlugin {

  object autoImport {
    //lazy val sethConfig = TaskKey[Unit]("seth-config", "Tests the seth-config autoplugin")

    lazy val defaultBindings : Seq[sbt.Def.Setting[_]] = Seq(
      promptTheme := {
        PromptTheme(List(
          text("\nseth", fg(green)),
          text(" on ", fg(magenta)),
          gitBranch(clean = fg(green), dirty = fg(yellow)),
          text(" in ", fg(magenta)),
          currentProject(fg(red)),
          text("> ", fg(magenta))
        ))
      }
    )
  }

  import autoImport._

  override def trigger = allRequirements
  override def requires = SbtPrompt
  override val projectSettings = defaultBindings
}
