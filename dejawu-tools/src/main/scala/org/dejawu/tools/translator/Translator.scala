package org.dejawu.tools.translator


case class Cmd(pkgname: String = null, mclass: String = null, input: String = null, output: String = null)


object CLI {
  import java.io.{InputStream,FileInputStream}
  import java.io.{OutputStream,PrintStream}
  import java.net.URL

  def inputStream(input: Option[String]) : InputStream =
    input match {
      case Some("-") => System.in
      case Some(_)   =>
        if(input.get.indexOf(":") == -1)
          new FileInputStream(input.get)
        else
          new URL(input.get).openStream
      case _         => System.in
    }

  def outputStream(output: Option[String]) : OutputStream =
    output match {
      case Some("-") => System.out
      case Some(_)   => new PrintStream(output.get)
      case _         => System.out
    }
}


class CLI(args : Array[String]) {
  // FIXME: http://stackoverflow.com/questions/7163364/how-to-handle-in-file-paths
  private val re   = "^~".r
  private val home = System.getProperty("user.home")

  private var pkgname : String = null
  private var mclass  : String = null
  private var input   : String = null
  private var output  : String = null

  private val parser = {
    new scopt.OptionParser[Unit]("translator") {
      head("""usage: translator [<input>] [<output>]
           |synopsis:
           |  Translate a HTML file to ScalaJS, employing Scalatags and Dejawu.
           |  More info on ScalaJS:      https://github.com/scala-js/scala-js
           |               Scalatags:    https://github.com/lihaoyi/scalatags
           |               Dejawu:       https://github.com/frgomes/dejawu
           |options:""".stripMargin)
      opt[String]("").hidden()
        .foreach { _ => pkgname = null }
      opt[String]('m', "main-class").valueName("[main-class]")
        .optional
        .foreach { o => mclass = o }
        .text("""Fully qualified class name""")
      arg[String]("<input>")
        .optional()
        .foreach { o => input = re.replaceFirstIn(o, home) }
        .text("""Input .html file or "-" (without quotes) for stdin""")
      arg[String]("<output>")
        .optional()
        .foreach { o => output = re.replaceFirstIn(o, home) }
        .text("""Output .scala file or "-" (without quotes) for stdout""")
      checkConfig { c => {
        if(mclass != null && mclass.trim.length > 0) {
          val parts = mclass.trim.split('.')
          pkgname = if(parts.length < 2) "" else parts.reverse.tail.reverse.mkString(".")
          mclass  = parts.length match {
            case 0 => "Noname"
            case 1 => mclass.trim
            case _ => parts.last
          }
        } else {
          mclass = "NoName"
        }
        success }}
    }
  }

  //TODO: consider class name taken from output file name, if any
  private def split(mclass: String) : (String, String) =
    if(mclass != null && mclass.trim.length > 0) {
      val parts = mclass.trim.split('.')
      val pkgname : String = if(parts.length < 2) "" else parts.reverse.tail.reverse.mkString(".")
      val clsname : String = parts.length match {
        case 0 => "Noname"
        case 1 => mclass.trim
        case _ => parts.last
      }
      ( pkgname, clsname )
    } else {
      ( null, null )
    }
 
  val parse : Option[Cmd] = if (!parser.parse(args)) None else Option(Cmd(pkgname, mclass, input, output))

}


object Translator {
  def main(args: Array[String]) {
    val cmd  = new CLI(args).parse
    val tool = new Translator
    if (cmd.isDefined)
      tool.translate(
        Option(cmd.get.pkgname),
        Option(cmd.get.mclass),
        CLI.inputStream(Some(cmd.get.input)),
        CLI.outputStream(Some(cmd.get.output)) )
  }
}


class Translator {
  import java.io.{InputStream,OutputStream}
  import scala.xml.{Elem, XML, NodeSeq, Node}
  import scala.xml.factory.XMLLoader
  import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl

  def escape(s: String) = if(s.indexOf("-") == -1) s else s"`$s`"

  def tripleQuotes : StringBuilder =
    new StringBuilder + ('"') + ('"') + ('"')

  def nospaces(s: String) : StringBuilder = {
    val sb = new StringBuilder
    if(s.trim.length > 0)
      if(s.intersect(List('\n', '\r', '\f')).length == 0) {
        sb += '"'
        sb ++= s
        sb += '"' }
      else
        sb ++= tripleQuotes.append(s.replaceAllLiterally("\"", "&quot;")).append(tripleQuotes)
    sb
  }

  def process(node: Node, level: Int) : StringBuilder = {
    val sb = new StringBuilder
    // find attribute ``data-dojo-type``, if any
    val djtype : Option[Seq[Node]] =
      node.attributes
        .filter(attr => attr.key == "data-dojo-type")
        .headOption
        .map(attr => attr.value)
    // obtain tag
    val tag : String =
      djtype match {
        case None => node.label
        case _ : Option[Seq[Node]] => djtype.get.head.text.replace("/", ".")
      }
    if(tag=="#PCDATA") {
      // copy text from #PCDATA
      sb ++= nospaces(node.text)
    } else {
      // otherwise copy tag ...
      if(level > 0) sb ++= "\n"
      sb ++= "  " * level
      sb ++= tag
      sb ++= "("
      // ... and attributes
      sb ++= node.attributes
        .filter(attr => attr.key != "data-dojo-type")
        .map(attr =>
          new StringBuilder()
            .append(escape(attr.key))
            .append(" := ")
            .append('"')
            .append(attr.value.head.text.replaceAllLiterally("\"", "&quot;"))
            .append('"')
            .toString)
        .mkString(", ")
      sb ++= ")"
      // ... and process children recursively
      if(node.child.length > 0) {
        sb ++= "("
        sb ++= process(node.child, level+1)
        sb ++= ")"
      }
    }
    sb
  }

  def process(nodes : NodeSeq, level : Int) : StringBuilder =
    nodes.foldLeft(new StringBuilder)(
      (sb, node) => sb ++= process(node, level).toString)

  def prologue(pkgname: Option[String], mclass : Option[String]) : StringBuilder =
    if(mclass.isDefined) {
      val sb = new StringBuilder
      if (pkgname.isDefined) sb ++= """package ${pkgname}\n\n"""
      sb ++= """import scala.scalajs.js
               |import js.annotation.JSExport
               |import js.Dynamic.{ global => g }
               | 
               |import scalatags.Text.all._
               |import org.dejawu.DojoText.all._
               | 
               | 
               |object ${clsname} extends js.JSApp {
               | 
               |  def main() = {
               |    val container = g.document.getElementById("container")
               |    container.innerHTML = render().toString()
               |  }
               | 
               |  private def render() =
               |""".stripMargin
  } else {
    new StringBuilder
  }

  def epilogue(pkgname : Option[String], mclass : Option[String]) : StringBuilder =
    if(pkgname.isDefined && mclass.isDefined)
      new StringBuilder("\n\n}")
    else
      new StringBuilder

  def translate(pkgname: Option[String], mclass: Option[String], is: InputStream) : StringBuilder = {
    val factory = new SAXFactoryImpl
    factory.setNamespaceAware(false)
    val loader : XMLLoader[Elem] = XML.withSAXParser(factory.newSAXParser())
    val root : scala.xml.Elem = loader.load(is)
    val body : NodeSeq = root \\ "body"
    prologue(pkgname, mclass)
      .append(process(body, 0))
      .append(epilogue(pkgname, mclass))
  }

  def translate(pkgname: Option[String], mclass: Option[String], is: InputStream, os: OutputStream)  : Unit =
    os.write( translate(pkgname, mclass, is).toString.getBytes )
}
