logLevel := Level.Info

scalaVersion := "2.10.4"  // "2.11.1" does not work :(


 //
// Repositories
///
resolvers += Resolver.url("scala-js-releases",
  url("http://dl.bintray.com/content/scala-js/scala-js-releases"))(Resolver.ivyStylePatterns)

resolvers += Resolver.url("scala-js-snapshots",
  url("http://repo.scala-js.org/repo/snapshots/"))(Resolver.ivyStylePatterns)

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Typesafe Maven Repository" at "http://repo.typesafe.com/typesafe/maven-releases/"


 //
// SBT plugins
///
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.3") // "2.3.0" does not work yet :(

addSbtPlugin("org.scala-lang.modules.scalajs" %% "scalajs-sbt-plugin" % "0.4.4")

addSbtPlugin("org.ensime" %% "ensime-sbt-cmd" % "0.1.4-SNAPSHOT")

//addSbtPlugin("com.typesafe.sbt" % "sbt-start-script" % "0.10.0")

//addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.1")

//addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")
