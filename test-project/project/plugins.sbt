// build root project
lazy val root = Project("plugins", file(".")) dependsOn(sethConfig)

// depends on the awesomeOS project
lazy val awesomeOS = file("..").getAbsoluteFile.toURI
