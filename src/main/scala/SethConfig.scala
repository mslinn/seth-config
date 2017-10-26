import com.typesafe.config.Config

object Colors {
  import java.util.{List => JList}
  import scala.collection.JavaConverters._

  // Taken from https://github.com/agemooij/sbt-prompt/blob/master/src/main/scala/com/scalapenos/sbt/prompt/Colors.scala
  val bgNoColor: String = "\u001b[49m"
  val fgNoColor: String =	"\u001b[39m"

  def apply(colors: JList[String]): Colors =
    colors.asScala.toList match {
      case Nil          => Colors(fgNoColor, bgNoColor)
      case List(bg)     => Colors(fgNoColor, bg)
      case List(fg)     => Colors(fg, bgNoColor)
      case List(fg, bg) => Colors(fg, bg)
  }
}

case class Colors(foreground: String, background: String) {
  def toHex(str: String): String = str.toCharArray.map { c =>
    if (c.isValidChar) c.toString else s"\\u${ c.toInt }"
  }.mkString

  override def toString: String =
    s"Colors(${ toHex(foreground) }, ${ toHex(background) })"
}

object Prop {
  def apply(config: Config, name: String) =
    new Prop(
      name   = name,
      value  = parseValue(config, name),
      colors = parseColors(config, name),
      show   = config.getBoolean(s"$name-show")
    )

  def parseValue(config: Config, name: String): String =
    try { config.getString(name) } catch { case e: Exception => e.getMessage }

  def parseColors(config: Config, name: String): Colors =
    try {
      Colors(config.getStringList(s"$name-colors"))
    } catch {
      case _: Exception => Colors(Colors.fgNoColor, Colors.bgNoColor)
    }
}

case class Prop(name: String, value: String, colors: Colors, show: Boolean=true) {
  def toHex(str: String): String = str.toCharArray.map { c =>
    if (c.isValidChar) c.toString else s"\\u${ c.toInt }"
  }.mkString

  override def toString: String = s"Prop(name = $name, value = ${ toHex(value) }, colors = $colors, show = $show)"
}

case class Prompt(
  name: Prop,
  suffix: Prop,
  sbtProject: Prop,
  gitBranch: Prop, // only includes clean branch colors
  showColors: Boolean,
  gitBranchDirtyColors: Colors = Colors("yellow",  Colors.bgNoColor),
  miscTextColors: Colors       = Colors("magenta", Colors.bgNoColor)
) {
  override def toString: String =
    s"""Prompt(
       |  name = $name,
       |  suffix = $suffix,
       |  sbtProject = $sbtProject,
       |  gitBranch = $gitBranch,
       |  showColors = $showColors,
       |  gitBranchDirtyColors = $gitBranchDirtyColors,
       |  miscTextColors = $miscTextColors
       |)""".stripMargin
}

case class SethConfig(prompt: Prompt) {
  override def toString: String = s"SethConfig(prompt = $prompt)"
}

object SethConfig {
  lazy val sethConfig: Config = com.typesafe.config.ConfigFactory.load("seth.conf").getConfig("seth")
  lazy val promptConfig: Config = sethConfig.getConfig("prompt")

  def apply: SethConfig = {
    val prompt: Prompt = Prompt(
      name       = Prop(promptConfig, "name"),
      suffix     = Prop(promptConfig, "suffix"),
      sbtProject = Prop(promptConfig, "sbt-project-name"),
      gitBranch  = Prop(promptConfig, "git-branch"),
      gitBranchDirtyColors = Colors(promptConfig.getStringList("git-branch-colors-dirty")),
      miscTextColors       = Colors(promptConfig.getStringList("misc-text-colors")),
      showColors           = promptConfig.getBoolean("show-colors")
    )
    SethConfig(prompt)
  }
}

object Main extends App {
  val sethConfig = SethConfig.apply
  println(sethConfig)
}
