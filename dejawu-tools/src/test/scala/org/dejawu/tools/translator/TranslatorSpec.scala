package org.dejawu.tools.translator

import java.io.InputStream
import org.scalatest._


class TranslatorSpec extends FeatureSpec with GivenWhenThen {

  val tool = new Translator

  feature("Ability parse command line arguments") {
    scenario("Parsing a command line with simple file names") {
      val cmd = new CLI(Array("Example.html", "Example.scala")).parse
      assert( cmd.isDefined )
      assert( cmd.get.input  === "Example.html"  )
      assert( cmd.get.output === "Example.scala" )
    }
    scenario("Parsing a command line with file names relative to the home directory") {
      val cmd = new CLI(Array("~/demo.html", "~/tmp/demo.scala")).parse
      assert( cmd.isDefined )
      val home = System.getProperty("user.home")
      assert( cmd.get.input  === s"${home}/demo.html"  )
      assert( cmd.get.output === s"${home}/tmp/demo.scala" )
    }

    //FIXME: allow "-" and "--" as arguments in http://github.com/scopt/scopt

    // scenario("Parsing a command line specifying standard input") {
    //   val cmd = new CLI(Array("-", "Example.scala")).parse
    //   assert( cmd.isDefined )
    //   assert( cmd.get.input  === "-" )
    //   assert( cmd.get.output === "Example.scala" )
    // }
    // scenario("Parsing a command line specifying standard output") {
    //   val cmd = new CLI(Array("Example.html", "-")).parse
    //   assert( cmd.isDefined )
    //   assert( cmd.get.input  === "Example.html" )
    //   assert( cmd.get.output === "-" )
    // }
    // scenario("Parsing a command line with both standard input and standard output") {
    //   val cmd = new CLI(Array("-", "-")).parse
    //   assert( cmd.isDefined )b
    //   assert( cmd.get.input  === "-" )
    //   assert( cmd.get.output === "-" )
    // }
    // scenario("Parsing a command line with standard output missing") {
    //   val cmd = new CLI(Array("Example.html")).parse
    //   assert( cmd.isDefined )
    //   assert( cmd.get.input  === "Example.html" )
    //   assert( cmd.get.output === None )
    // }
    // scenario("Parsing a command line with both standard input andstandard output missing") {
    //   val cmd = new CLI(Array("Example.html")).parse
    //   assert( cmd.isDefined )
    //   assert( cmd.get.input  === None )
    //   assert( cmd.get.output === None )
    // }

    //TODO: these test cases
    // The following test cases call System.exit and interrupt execution of the test suite :(

    // scenario("Requesting help") {
    //   val cmd = new CLI(Array("--help"))
    // }
    // scenario("Requesting version information") {
    //   val cmd = new CLI(Array("--version"))
    // }
  }

  import java.io._

  feature("Ability to generate output") {
    scenario("Parsing a simple HTML") {
      snippet(
        "<html><body>Hello</body></html>",
        """body()("Hello")""".stripMargin)
    }
    scenario("Parsing a simple HTML with attribute") {
      snippet(
        """<html><body style="tundra">Hello</body></html>""",
        """body(style := "tundra")("Hello")""".stripMargin)
    }
    scenario("Parsing a very simple HTML with one Dojo widget") {
      snippet(
        """<html>
            <body>
              <div>
                <select data-dojo-type="dijit/form/Select" name="select1">
                  <option value="TN">Tennessee</option>
                  <option value="VA">Virginia</option>
                  <option value="WA">Washington</option>
                  <option value="FL">Florida</option>
                  <option value="CA">California</option>
                </select>
              </div>
            </body>
          </html>""",
        """body()(
          |  div()(
          |    dijit.form.Select(name := "select1")(
          |      option(value := "TN")("Tennessee")
          |      option(value := "VA")("Virginia")
          |      option(value := "WA")("Washington")
          |      option(value := "FL")("Florida")
          |      option(value := "CA")("California"))))""".stripMargin)
    }
    scenario("Translate Dojo Demo 'Theme Previewer'") {
      val html  = "https://raw.githubusercontent.com/dojo/demos/master/themePreviewer/demo.html"
      val scala = "/tmp/demo.scala"
      val cmd = new CLI(Array(html, scala)).parse
      assert(cmd.isDefined)
      val tool = new Translator
      val is = CLI.inputStream(Some(cmd.get.input))
      val os = CLI.outputStream(Some(cmd.get.output))
      tool.translate( is, os )
    }
  }

  private def snippet(html: String, scala: String) : Unit = {
    val is = new ByteArrayInputStream(html.getBytes("UTF-8"))
    val os = new ByteArrayOutputStream
    val tool = new Translator
    tool.translate(is, os)
    assert(new String(os.toByteArray, "UTF-8") === scala)
  }

}
