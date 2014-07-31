package org.dejawu.tools.translator

import java.io.InputStream
import org.scalatest._


class TranslatorSpec extends FeatureSpec with GivenWhenThen {
  feature("Ability parse command line arguments") {
/*
    scenario("Parsing a command line with simple file names") {
      val cmd = new CLI(Array("Example.html", "Example.scala")).parse
      assert( cmd.isDefined )
      assert( cmd.get.pkgname === null )
      assert( cmd.get.clsname === null )
      assert( cmd.get.input   === "Example.html"  )
      assert( cmd.get.output  === "Example.scala" )
    }
    scenario("Parsing a command line with simple file names and main class") {
      val cmd = new CLI(Array("-m", "org.dejawu.demos.ThemePreviewer", "Example.html", "Example.scala")).parse
      assert( cmd.isDefined )
      assert( cmd.get.pkgname === "org.dejawu.demos" )
      assert( cmd.get.clsname === "ThemePreviewer" )
      assert( cmd.get.input   === "Example.html"  )
      assert( cmd.get.output  === "Example.scala" )
    }
    scenario("Parsing a command line with file names relative to the home directory") {
      val cmd = new CLI(Array("~/demo.html", "~/tmp/demo.scala")).parse
      assert( cmd.isDefined )
      val home = System.getProperty("user.home")
      assert( cmd.get.pkgname === null )
      assert( cmd.get.clsname === null )
      assert( cmd.get.input   === s"${home}/demo.html"  )
      assert( cmd.get.output  === s"${home}/tmp/demo.scala" )
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

  feature("Ability to escape attributes") {
    scenario("Attributes do not need to be escaped") {
      val tool = new Translator
      assert( tool.escape("name") === "name"   )
      assert( tool.escape("__a_") === "__a_" )
      assert( tool.escape("a__b") === "a__b" )
      assert( tool.escape("_var") === "_var" )
    }
    scenario("Attributes need to be escaped") {
      val tool = new Translator
      assert( tool.escape("type")  === "`type`"  )
      assert( tool.escape("for")   === "`for`"   )
      assert( tool.escape("class") === "`class`" )
      assert( tool.escape("var")   === "`var`"   )
      assert( tool.escape("a-b")   === "`a-b`"   )
      assert( tool.escape("data-dojo-type")   === "`data-dojo-type`"   )
    }
  }

  feature("Ability to generate output") {
    scenario("Parsing a simple HTML") {
      snippet(
        null, null,
        "<html><body>Hello</body></html>",
        """body()("Hello")""".stripMargin)
    }
    scenario("Parsing a simple HTML with prologue") {
      snippet(
        null, "Name",
        "<html><body>Hello</body></html>",
        """import scala.scalajs.js
          |import js.annotation.JSExport
          |import js.Dynamic.{ global => g }
          | 
          |import scalatags.Text.all._
          |import org.dejawu.DojoText.all._
          | 
          | 
          |object Name extends js.JSApp {
          | 
          |  def main() = {
          |    val container = g.document.getElementById("container")
          |    container.innerHTML = render().toString()
          |  }
          | 
          |  private def render() =
          |body()("Hello")
          |
          |}""".stripMargin)
    }
    scenario("Parsing a simple HTML with attribute") {
      snippet(
        null, null,
        """<html><body style="tundra">Hello</body></html>""",
        """body(style := "tundra")("Hello")""".stripMargin)
    }
    scenario("Parsing a simple HTML with attribute with prologue") {
      snippet(
        "fully.qualified.class", "Name",
        """<html><body style="tundra">Hello</body></html>""",
        """package fully.qualified.class
          |
          |import scala.scalajs.js
          |import js.annotation.JSExport
          |import js.Dynamic.{ global => g }
          | 
          |import scalatags.Text.all._
          |import org.dejawu.DojoText.all._
          | 
          | 
          |object Name extends js.JSApp {
          | 
          |  def main() = {
          |    val container = g.document.getElementById("container")
          |    container.innerHTML = render().toString()
          |  }
          | 
          |  private def render() =
          |body(style := "tundra")("Hello")
          |
          |}""".stripMargin)
    }
    scenario("Parsing a very simple HTML with one Dojo widget") {
      snippet(
        null, null,
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
          |      option(value := "TN")("Tennessee"),
          |      option(value := "VA")("Virginia"),
          |      option(value := "WA")("Washington"),
          |      option(value := "FL")("Florida"),
          |      option(value := "CA")("California"))))""".stripMargin)
    }

    scenario("Translate my humble Dojo experiment from purejs.scala.html") {
      val fqcn  = "org.dejawu.demos.ScalaJSExample"
      val html  = "/home/rgomes/workspace/dejawu/dejawu-play/app/views/purejs.scala.html"
      val scala = "/tmp/ScalaJSExample.scala"
      val cmd = new CLI(Array("-m", fqcn, html, scala)).parse
      assert(cmd.isDefined)
      val tool = new Translator
      val pkgname  = Option(cmd.get.pkgname)
      val clsname  = Option(cmd.get.clsname)
      val is = CLI.inputStream(Some(cmd.get.input))
      val os = CLI.outputStream(Some(cmd.get.output))
      tool.translate( pkgname, clsname, is, os )
    }
 */

    scenario("Translate Dojo Demo 'Theme Previewer'") {
      val fqcn  = "org.dejawu.demos.ThemePreviewer"
      val html  = "https://raw.githubusercontent.com/dojo/demos/master/themePreviewer/demo.html"
      val scala = "dejawu-scalajs/src/main/scala/org/dejawu/demos/ThemePreviewer.scala"
      val cmd = new CLI(Array("-k", "-m", fqcn, html, scala)).parse
      assert(cmd.isDefined)
      val tool = new Translator
      val pkgname  = Option(cmd.get.pkgname)
      val clsname  = Option(cmd.get.clsname)
      val is = CLI.inputStream(Some(cmd.get.input))
      val os = CLI.outputStream(Some(cmd.get.output))
      tool.translate( cmd.get, is, os )
    }
  }

  private def snippet(cmd: Cmd, html: String, scala: String) : Unit = {
    import java.io._
    val tool = new Translator
    val os = new ByteArrayOutputStream
    tool.translate(cmd, new ByteArrayInputStream(html.getBytes("UTF-8")), os)
    assert(new String(os.toByteArray, "UTF-8") === scala)
  }

}
