lazy val root = Project("plugins", file(".")) dependsOn sethPlugin

lazy val sethPlugin = file("..").getAbsoluteFile.toURI
