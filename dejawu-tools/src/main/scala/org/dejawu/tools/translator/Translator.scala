package org.dejawu.tools.translator


case class Cmd(pkgname: String = null, clsname: String = null, keep: Boolean = false, input: String = null, output: String = null)


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

  private val parser = 
    new scopt.OptionParser[Cmd]("translator") {
      head("""usage: translator [<input>] [<output>]
           |synopsis:
           |  Translate a HTML file to ScalaJS, employing Scalatags and Dejawu.
           |  More info on ScalaJS:      https://github.com/scala-js/scala-js
           |               Scalatags:    https://github.com/lihaoyi/scalatags
           |               Dejawu:       https://github.com/frgomes/dejawu
           |options:""".stripMargin)
      opt[Unit]('k', "keep-markup").valueName("[keep-markup]")
        .optional
        .action { (o, c) => c.copy( keep = true ) }
        .text("""Keep the markup as it is, without employing dejawu tags.""")
      opt[String]('m', "main-class").valueName("[main-class]")
        .action { (o, c) => c.copy( clsname = o ) }
        .text("""Fully qualified class name""")
      arg[String]("<input>")
        .optional()
        .action { (o, c) => c.copy( input = re.replaceFirstIn(o, home) ) }
        .text("""Input .html file or "-" (without quotes) for stdin.""")
      arg[String]("<output>")
        .optional()
        .action { (o, c) => c.copy( output = re.replaceFirstIn(o, home) ) }
        .text("""Output .scala file or "-" (without quotes) for stdout.""")
    }

  private def validate(c : Option[Cmd]) : Option[Cmd] =
    if(!c.isDefined) c else
      if(c.get.clsname != null && c.get.clsname.trim.length > 0) {
        val parts = c.get.clsname.trim.split('.')
        val pkgname : String = if(parts.length < 2) "" else parts.reverse.tail.reverse.mkString(".")
        val clsname : String = parts.length match {
          case 0 => "Noname"
          case 1 => c.get.clsname.trim
          case _ => parts.last }
        Option(Cmd(pkgname, clsname, c.get.keep, c.get.input, c.get.output)) }
      else { Option(Cmd(null, null, c.get.keep, c.get.input, c.get.output)) }

  val parse : Option[Cmd] = validate(parser.parse(args, Cmd()))

}


object Translator {
  def main(args: Array[String]) {
    val cmd  = new CLI(args).parse
    val tool = new Translator
    if (cmd.isDefined) tool.translate(cmd.get)
  }
}


class Translator {
  import java.io.{InputStream,OutputStream}
  import scala.xml.{Elem, XML, NodeSeq, Node}
  import scala.xml.factory.XMLLoader
  import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl

  private val keywords : Set[String] = Set(
    "for", "false", "package", "lazy", "this", "try", "protected", "private", "return",
    "if", "do", "override", "else", "macro", "abstract", "super", "yield", "finally", "null",
    "object", "match", "import", "forSome", "implicit", "final", "then", "trait", "new",
    "while", "with", "true", "class", "extends", "def", "case", "type", "catch", "throw",
    "sealed", "var", "val")
  //FIXME: use the code below instead when everything flips to Scala 2.11
  //  new scala.tools.nsc.Global(new scala.tools.nsc.Settings)
  //    .nme
  //    .keywords
  //    .map(t => t.toString)

  def escape(s: String) = if(s.indexOf("-") > -1 || keywords.contains(s.trim)) s"`$s`" else s

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

  def process(cmd: Cmd, node: Node, level: Int) : StringBuilder = {
    val sb = new StringBuilder
    // find attribute ``data-dojo-type``, if any
    val djtype : Option[Seq[Node]] =
      node.attributes
        .filter(attr => !cmd.keep && attr.key == "data-dojo-type")
        .headOption
        .map(attr => attr.value)
    // obtain tag
    val tag : String =
      djtype match {
        case None => node.label
        case _ : Option[Seq[Node]] => {
          val djtag = djtype.get.head.text.replace("/", ".")
          println(s"${djtag}=${node.label}")
          djtag
        }
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
        .filter(attr => cmd.keep || attr.key != "data-dojo-type")
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
        sb ++= process(cmd, node.child, level+1)
        sb ++= ")"
      }
    }
    sb
  }

  def process(cmd: Cmd, nodes : NodeSeq, level : Int) : StringBuilder =
    nodes
      .map(node => process(cmd, node, level))
      .filter(sbNode => sbNode.length > 0)
      .foldLeft(new StringBuilder)((sb, sbNode) => {
        if(sb.length > 0) sb ++= ","
        sb ++= sbNode })

  def prologue(pkgname: Option[String], clsname : Option[String]) : StringBuilder =
    if(clsname.isDefined) {
      val sb = new StringBuilder
      if (pkgname.isDefined) sb ++= s"""package ${pkgname.get}""" + "\n\n"
      sb ++= s"""import scala.scalajs.js
                |import js.annotation.JSExport
                |import js.Dynamic.{ global => g }
                | 
                |import scalatags.Text.all._
                |import org.dejawu.DojoText.all._
                | 
                | 
                |object ${clsname.get} extends js.JSApp {
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

  def epilogue(pkgname : Option[String], clsname : Option[String]) : StringBuilder =
    if(clsname.isDefined && clsname.isDefined)
      new StringBuilder("\n\n}")
    else
      new StringBuilder

  def translate(cmd: Cmd, is: InputStream) : StringBuilder = {
    val factory = new SAXFactoryImpl
    factory.setNamespaceAware(false)
    val loader : XMLLoader[Elem] = XML.withSAXParser(factory.newSAXParser())
    val root : scala.xml.Elem = loader.load(is)
    val body : NodeSeq = root \\ "body"
    prologue(Option(cmd.pkgname), Option(cmd.clsname))
      .append(process(cmd, body, 0))
      .append(epilogue(Option(cmd.pkgname), Option(cmd.clsname)))
  }

  def translate(cmd: Cmd, is: InputStream, os: OutputStream)  : Unit =
    os.write( translate(cmd, is).toString.getBytes )

  def translate(cmd: Cmd) : Unit =
    translate(cmd, CLI.inputStream(Some(cmd.input)), CLI.outputStream(Some(cmd.output)) )
}
