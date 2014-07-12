import sbt._
import Keys._
import java.io.{ File => OutputStream, FileOutputStream }

import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._


object DejawuBuild extends Build {

  val depsCommon = Seq(
    "org.scalatest" %% "scalatest" % "2.2.0" % "test"
  )

  val depsTools = depsCommon ++ Seq(
    "org.rogach" %% "scallop" % "0.9.5"
  )

  val depsJS = Seq(
    "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.6",
    "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test",
    "com.scalatags"                  %% "scalatags" % "0.3.5"
  )



  val basedir = file(".")



  lazy val root = Project(id="dejawu", base=basedir)
    .aggregate(tools,scalajs)

  lazy val tools = Project(id="dejawu-tools", base=file("dejawu-tools"))
    .settings(
      libraryDependencies ++= depsTools)
      //-- unmanagedSourceDirectories in Compile += basedir / "dejawu-shared" / "src" / "main" / "scala")

  lazy val shared = Project(id="dejawu-shared", base=file("dejawu-shared"))
    .settings(
      libraryDependencies ++= depsJS)

  lazy val scalajs = Project(id="dejawu-scalajs", base=file("dejawu-scalajs"))
    .settings(scalaJSSettings: _*)
    .settings(
      libraryDependencies ++= depsJS,
      unmanagedSourceDirectories in Compile +=  basedir / "dejawu-shared" / "src" / "main" / "scala",
      sourceGenerators           in Compile <+= (dejawuCodeGenerator in Compile),
      dejawuCodeGenerator        in Compile <<=
        (javaSource in Compile, dependencyClasspath in Runtime in tools) map {
          (javaSource, cp) => runCodeGenerator(javaSource, cp.files)
        })
    .dependsOn(tools)

  //-- lazy val dejawuJVM = project.in(file("dejawu-play")).enablePlugins(play.PlayScala).settings(play.Project.playScalaSettings: _*).settings(
  //lazy val dejawuJVM = (project in file("dejawu-play")).enablePlugins(PlayScala).settings(
  //  name := "dejawu",
  //  unmanagedSourceDirectories in Compile += basedir / "dejawu-shared" / "src" / "main" / "scala"
  //)

  val dejawuCodeGenerator = TaskKey[Seq[File]]("codegen", "Generate DojoTags.scala")

  def runCodeGenerator(javaSource: File, classpath: Seq[File]): Seq[File] = {
    val dojoTags   : String       = "dejawu-scalajs/src/main/scala/org/dejawu/DojoTags.scala"
    val mainClass  : String       = "org.dejawu.tools.DojoGen"
    val javaHome   : Option[File] = None
    val jvmOptions : Seq[String]  = Nil
    val args       : Seq[String]  = List("-c", "dejawu-tools/src/main/resources/widgets.properties", dojoTags)
    val cwd        : Option[File] = None
    val os = new FileOutputStream("/dev/null") //TODO: should redirect stdout instead of passing via command line
    try {
      val i = new Fork.ForkScala(mainClass).fork(
        javaHome,        // Option[File],
        jvmOptions,      // Seq[String],
        classpath,       // Iterable[File],
        args,            // Seq[String],
        cwd,             // workingDirectory: Option[File],
        false,           // Boolean,
        CustomOutput(os) // OutputStrategy
      ).exitValue
      if (i != 0) {
        error("Dejawu Code Generator is in trouble")
      }
    } finally {
      os.close()
    }
    List(file(dojoTags))
  }
}
