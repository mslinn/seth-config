lazy val root = Project("plugins", file(".")) dependsOn sethConfig

lazy val sethConfig = file("..").getAbsoluteFile.toURI
