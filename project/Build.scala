import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._

import com.typesafe.sbt.packager.universal.UniversalKeys
import play.Play._


object ApplicationBuild extends Build with UniversalKeys {

  val appName       = "dejawu"
  val toolsSrcDir   = appName + "-tools"
  val sharedSrcDir  = appName + "-shared"
  val scalajsSrcDir = appName + "-scalajs"
  val webappSrcDir  = appName + "-play"

   //
  // default settings
  ///////////////////

  override lazy val settings = super.settings :+ {
    crossScalaVersions := Seq(
      // the first version is the preferred one
      "2.11.2", "2.10.4"
    )}


   //
  // projects
  ///////////

  override def rootProject = Some(webapp)

  lazy val webapp = Project(id = webappSrcDir, base = file(webappSrcDir))
    .enablePlugins(play.PlayScala)
    .settings(webappSettings: _*)
    .aggregate (tools,scalajs)

  lazy val scalajs = Project(id = scalajsSrcDir, base = file(scalajsSrcDir))
    .settings(scalajsSettings: _*)
    .dependsOn(tools)

  lazy val shared = Project(id = sharedSrcDir, base = file(sharedSrcDir))
    .settings(sharedSettings: _*)
    .dependsOn(tools)

  lazy val tools = Project(id = toolsSrcDir, base = file(toolsSrcDir))
    .settings(toolsSettings: _*)
    .settings(assemblySettings: _*)

   //
  // project settings
  ///////////////////

  val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  lazy val webappSettings =
    Seq(
      name := webappSrcDir,
      version := Versions.app,
      libraryDependencies ++= Dependencies.webapp,
      // dependsOn
      compile in Compile <<= (compile in Compile) dependsOn (fastOptJS in (scalajs, Compile)),
      dist               <<= dist                 dependsOn (fullOptJS in (scalajs, Compile))
                                                  dependsOn (assembly  in tools),
      // special settings
      persistLauncher := false, // we don't need a package launcher
      scalajsOutputDir := (crossTarget in Compile).value / "classes" / "public" / "javascripts",
      commands += preStartCommand
    ) ++ (
      // ask scalajs project to put its outputs in scalajsOutputDir
      //Seq(packageExternalDepsJS, packageInternalDepsJS, packageExportedProductsJS, packageLauncher, fastOptJS, fullOptJS)
      Seq(packageExternalDepsJS, packageInternalDepsJS, packageExportedProductsJS, fastOptJS, fullOptJS)
        .map { packageJSKey => crossTarget in (scalajs, Compile, packageJSKey) := scalajsOutputDir.value }
    ) ++ sharedDirectorySettings

  lazy val scalajsSettings =
    scalaJSSettings ++ Seq(
      name := scalajsSrcDir,
      version := Versions.app,
      libraryDependencies ++= Dependencies.scalajs,
      // special settings
      persistLauncher := true,
      persistLauncher in Test := false
    ) ++ sharedDirectorySettings

  lazy val sharedSettings =
    Seq(
      name := sharedSrcDir,
      libraryDependencies ++= Dependencies.shared
    )

  lazy val toolsSettings =
    Seq(
      name := toolsSrcDir,
      version := Versions.app,
      resolvers += Resolver.sonatypeRepo("public"),
      libraryDependencies ++= Dependencies.tools
    )

  lazy val sharedDirectorySettings = Seq(
    unmanagedSourceDirectories   in Compile += file((file(sharedSrcDir) / "src" / "main" / "scala").getCanonicalPath),
    unmanagedSourceDirectories   in Test    += file((file(sharedSrcDir) / "src" / "test" / "scala").getCanonicalPath),
    unmanagedResourceDirectories in Compile += file(sharedSrcDir) / "src" / "main" / "resources",
    unmanagedResourceDirectories in Test    += file(sharedSrcDir) / "src" / "test" / "resources"
  )

   //
  // intercepts play.Play.playStartCommand and runs fullOptJS before it
  /////////////////////////////////////////////////////////////////////

  // Use reflection to rename the 'start' command to 'play-start'
  Option(play.Play.playStartCommand.getClass.getDeclaredField("name")) map { field =>
    field.setAccessible(true)
    field.set(playStartCommand, "play-start")
  }

  // The new 'start' command optimises the JS before calling the Play 'start' renamed 'play-start'
  val preStartCommand = Command.args("start", "<port>") {
    (state: State, args: Seq[String]) =>
      Project.runTask(fullOptJS in (scalajs, Compile), state)
      state.copy(remainingCommands = ("play-start " + args.mkString(" ")) +: state.remainingCommands)
  }
}


object Dependencies {
  val utest     = Seq()

  //FIXME: replace scalatest by utest
  val scalatest = Seq(
    "org.scalatest" %% "scalatest" % "2.2.0" )

  val tools   = scalatest ++ Seq(
    "com.github.scopt"       %% "scopt"     % Versions.scopt,
    "org.ccil.cowan.tagsoup" %  "tagsoup"   % Versions.tagsoup )

  val shared  = utest ++ Seq(
    "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % Versions.scalajsDom,
    "com.scalatags"                  %%% "scalatags"   % Versions.scalatags )

  val scalajs = shared ++ Seq(
    "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test" )

  val webapp  = shared ++ Seq()
}


object Versions {
  val app = "0.1.0-SNAPSHOT"
  val scalajsDom = "0.6"
  val scalatags = "0.4.0"
  val scopt = "3.2.0"
  val tagsoup = "1.2.1"
}



//----------------------------------------------------------------------------------------------------------
// Subproject dejawu-tools defines a code generator which employs code from ScalaJS and scalatags
//
// Status: With scalatags 0.3.8, the code generator became useless.
//         I've created sources by hand in the time being until scalatags stabilizes.
//----------------------------------------------------------------------------------------------------------
//
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

