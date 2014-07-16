import sbt._
import Keys._

// ScalaJS imports
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._

// Play Framework imports
import play.Play.autoImport._
import PlayKeys._



object DejawuBuild extends Build {

  val appName = "dejawu"
  val appVersion = "0.1-SNAPSHOT"

  scalaVersion := "2.11.1"

  val depsCommon = Seq(
    "org.scalatest" %% "scalatest" % "2.2.0" % "test"
  )

  val depsTools = depsCommon ++ Seq(
    "org.rogach" %% "scallop" % "0.9.5"
  )

  val depsJS = Seq(
    "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
    "org.scala-lang.modules.scalajs" %%  "scalajs-jasmine-test-framework" % scalaJSVersion % "test",
    "com.scalatags"                  %%  "scalatags" % "0.3.8"
  )


  val basedir : File   = file(".").getAbsoluteFile


  //lazy val root = Project(appName, basedir)
  //  .aggregate(shared,scalajs,webapp)
  //  .dependsOn(shared,scalajs,webapp)

  lazy val shared = Project(appName+"-shared", basedir / (appName+"-shared"))
    .settings(scalaJSSettings: _*) //FIXME:: something like this:: .enablePlugins(scalajs.ScalaJSPluin)
    .settings(
      //XXX sourceGenerators           in Compile <+= (dejawuCodeGenerator in Compile),
      //XXX dejawuCodeGenerator        in Compile <<=
      //XXX   (javaSource in Compile, dependencyClasspath in Runtime in tools) map {
      //XXX     (javaSource, cp) => runCodeGenerator(javaSource, cp.files) }
      libraryDependencies ++= depsJS)
    //TODO:: .depends(tools)

  lazy val scalajs = Project(appName+"-scalajs", basedir / (appName+"-scalajs"))
    .settings(scalaJSSettings: _*) //FIXME:: something like this:: .enablePlugins(scalajs.ScalaJSPluin)
    .settings(
      libraryDependencies ++= depsJS)
    .dependsOn(shared)

  lazy val webapp = Project(appName, basedir / (appName+"-play"))
    .enablePlugins(play.PlayScala)
    //.settings(
    //  unmanagedSourceDirectories in Compile += basedir / (appName+"-shared")  / "src" / "main" / "scala",
    //  unmanagedSourceDirectories in Compile += basedir / (appName+"-scalajs") / "src" / "main" / "scala" )
    .dependsOn(shared,scalajs)



  //----------------------------------------------------------------------------------------------------------
  // Subproject dejawu-tools defines a code generator which employs code from ScalaJS and scalatags
  //
  // Status: With scalatags 0.3.8, the code generator became useless.
  //         I've created sources by hand in the time being until scalatags stabilizes.
  //----------------------------------------------------------------------------------------------------------

  // lazy val tools = Project(appName+"-tools", basedir / (appName+"-tools"))
  //   .settings(
  //     libraryDependencies ++= depsTools)

  // val dejawuCodeGenerator = TaskKey[Seq[File]]("codegen", "Generate DojoTags.scala")
  // def runCodeGenerator(javaSource: File, classpath: Seq[File]): Seq[File] = {
  //   import java.io.{ File => OutputStream, FileOutputStream }
  //   val dojoTags   : String       = "dejawu-scalajs/src/main/scala/org/dejawu/DojoTags.scala"
  //   val mainClass  : String       = "org.dejawu.tools.DojoGen"
  //   val javaHome   : Option[File] = None
  //   val jvmOptions : Seq[String]  = Nil
  //   val args       : Seq[String]  = List("-c", "dejawu-tools/src/main/resources/widgets.properties", dojoTags)
  //   val cwd        : Option[File] = None
  //   val os = new FileOutputStream("/dev/null") //TODO: should redirect stdout instead of passing via command line
  //   try {
  //     val i = new Fork.ForkScala(mainClass).fork(
  //       javaHome,        // Option[File],
  //       jvmOptions,      // Seq[String],
  //       classpath,       // Iterable[File],
  //       args,            // Seq[String],
  //       cwd,             // workingDirectory: Option[File],
  //       false,           // Boolean,
  //       CustomOutput(os) // OutputStrategy
  //     ).exitValue
  //     if (i != 0) {
  //       error("Dejawu Code Generator is in trouble")
  //     }
  //   } finally {
  //     os.close()
  //   }
  //   // return empty list because the generated file
  //   // will be picked up anyway
  //   List()
  // }

}
