// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"


// plugins

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

//addSbtPlugin("com.lihaoyi" % "utest-js-plugin" % "0.1.8")

addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.5.2")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.1")
