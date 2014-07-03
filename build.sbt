scalaJSSettings

// import play.PlayScala


name := "dejawu"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.1"

ScalaJSKeys.persistLauncher := true

ScalaJSKeys.persistLauncher in Test := false
  
val depsJS = Seq(
  "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
  "org.scala-lang.modules.scalajs" %%  "scalajs-jasmine-test-framework" % scalaJSVersion % "test",
  "com.scalatags" %%% "scalatags" % "0.3.5"
)

val depsJVM = Seq(
  "org.scalatest" %% "scalatest" % "2.2.0" % "test"
)


lazy val root : Project = project.in(file("."))
  .aggregate(tools,shared,scalajs)

lazy val scalajs : Project = project.in(file("dejawu-scalajs")).settings(scalaJSSettings: _*).settings(
  name := "dejawu-scalajs",
  unmanagedSourceDirectories in Compile += root.base / "dejawu-shared" / "src" / "main" / "scala",
  libraryDependencies ++= depsJS
)

//-- lazy val dejawuJVM = project.in(file("dejawu-play")).enablePlugins(play.PlayScala).settings(play.Project.playScalaSettings: _*).settings(
//lazy val dejawuJVM = (project in file("dejawu-play")).enablePlugins(PlayScala).settings(
//  name := "dejawu",
//  unmanagedSourceDirectories in Compile += root.base / "dejawu-shared" / "src" / "main" / "scala"
//)

lazy val tools = project.in(file("dejawu-tools")).settings(
  name := "dejawu-tools",
  libraryDependencies ++= depsJVM,
  unmanagedSourceDirectories in Compile += root.base / "dejawu-shared" / "src" / "main" / "scala"
)

lazy val shared = project.in(file("dejawu-shared")).settings(
  name := "dejawu-shared",
  libraryDependencies ++= depsJS
)
