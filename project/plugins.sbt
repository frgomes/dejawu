logLevel := Level.Info

scalaVersion := "2.10.3" // "2.11.1" does not work :(

 //
// Repositories
///
resolvers ++= Seq(
  "Typesafe repository"       at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Maven Repository" at "http://repo.typesafe.com/typesafe/maven-releases/"
)

 //
// SBT plugins
///
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.3") // "2.3.0" does not work yet :(

addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.4.4")
