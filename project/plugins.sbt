logLevel := Level.Info

scalaVersion := "2.10.3"

 //
// Repositories
///
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

 //
// SBT plugins
///
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.3")

addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.4.4")
