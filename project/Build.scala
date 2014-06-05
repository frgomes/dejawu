import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._


object Build extends sbt.Build {

   //
  // Application
  ///
  val appName = "dejawu"
  val appVersion = "0.1-SNAPSHOT"

   //
  // Settings
  ///
  lazy val scalaSettings = play.Project.playScalaSettings ++ Seq(
    scalacOptions ++= Seq("-feature"))

  lazy val scalajsSettings =
    scalaJSSettings ++ Seq(
      name := appName + "-scalajs",
      version := appVersion,
      libraryDependencies ++= scalajsDeps
    )


  //
  // Dependencies
  ///
  val mockitoVersion = "1.9.5"

  lazy val rootDeps = Seq(
    // test
    "org.mockito" % "mockito-all" % mockitoVersion % "test" )

  lazy val scalajsDeps = Seq(
    "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.4",
    "com.scalatags" %% "scalatags" % "0.2.5-JS"
    // test -----
    , "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test"
  )


   //
  // Projects and modules
  ///

  // In future, this project will be demo website similar to
  // * http://dojotoolkit.org/api/
  // * http://dojotoolkit.org/reference-guide/1.9/dijit/
  lazy val root = play.Project(
    appName, appVersion,
    rootDeps,
    path = file(""),
    settings = scalaSettings
  ).settings(scalaSettings: _*)
    //TODO:: .dependsOn(scalajs)
    //TODO:: .aggregate(scalajs)


  //TODO: Module dejawu-scalajs is what we are really interested to write
  lazy val scalajs = Project(
    id   = appName + "-scalajs",
    base = file("scalajs")
  ) settings (scalajsSettings: _*)
  lazy val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

}