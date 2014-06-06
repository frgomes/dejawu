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
  // Dependencies
  ///
  val scalajs_v   = "0.4.4"
  val scalatags_v = "0.2.5"
  val mockito_v   = "1.9.5"

  val scalatags_js = scalatags_v + "-JS"

  val rootDeps = Seq(
    "com.scalatags" %% "scalatags" % scalatags_js
    // test -----
    , "org.mockito" % "mockito-all" % mockito_v % "test"
  )

  val scalajsDeps = Seq(
    "org.scala-lang.modules.scalajs" %% "scalajs-dom" % scalajs_v, // "0.4",
    "com.scalatags" %% "scalatags" % scalatags_js
    // test -----
    , "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test"
  )


  //
  // Settings
  ///
  val scalaSettings = play.Project.playScalaSettings ++ Seq(
    scalacOptions ++= Seq("-feature"))

  val scalajsSettings =
    scalaJSSettings ++ Seq(
      name := appName + "-scalajs",
      version := appVersion,
      libraryDependencies ++= scalajsDeps
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