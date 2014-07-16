resolvers += "Typesafe Plugin Repository" at "http://repo.typesafe.com/typesafe/releases/"

//-- other resolvers
//resolvers += "Sonatype snapshots"         at "http://oss.sonatype.org/content/repositories/snapshots/"
//resolvers += "Typesafe Maven Repository"  at "http://repo.typesafe.com/typesafe/maven-releases/"

//-- ScalaJS
addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.5.0")

//-- Play Framework
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.1")

//-- web plugins
addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.0.0")

//-- other web plugins
// addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.0")
// addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.0")
// addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.0")
// addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.1")
// addSbtPlugin("com.typesafe.sbt" % "sbt-mocha" % "1.0.0")
