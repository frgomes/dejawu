package org.dejawu.tools.translator


case class Cmd(input: String = null, output: String = null)


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
      arg[String]("<input>")
        .optional()
        .action { (o, c) => c.copy( input = re.replaceFirstIn(o, home) ) }
        .text("""Input .html file or "-" (without quotes) for stdin""")
      arg[String]("<output>")
        .optional()
        .action { (o, c) => c.copy( output = re.replaceFirstIn(o, home) ) }
        .text("""Output .scala file or "-" (without quotes) for stdout""")
    }

  val parse : Option[Cmd] = parser.parse(args, Cmd())
}


object Translator {
  def main(args: Array[String]) {
    val cmd  = new CLI(args).parse
    val tool = new Translator
    if (cmd.isDefined)
      tool.translate(
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

  def nospaces(s: String) : String = if(s.trim.length == 0) "" else s""""${s}""""

  def process(node: Node, level: Int) : StringBuilder = {
    val sb = new StringBuilder
    val attrs  : scala.xml.MetaData = node.attributes
    val djtype : Option[Seq[Node]] =
      attrs
        .filter(attr => attr.key == "data-dojo-type")
        .headOption
        .map(attr => attr.value)
    val tag : String =
      djtype match {
        case None => node.label
        case _ : Option[Seq[Node]] => djtype.get.head.text.replace("/", ".")
      }
    if(tag=="#PCDATA") {
      sb ++= nospaces(node.text)
    } else {
      if(level > 0) sb ++= "\n"
      sb ++= "  " * level
      sb ++= tag
      sb ++= "("
      sb ++= attrs
        .filter(attr => attr.key != "data-dojo-type")
        .foldLeft(new StringBuilder)((sb2, attr) => {
          if(sb2.length > 0) sb ++= ", "
          sb2 ++= s"""${escape(attr.key)} := "${attr.value.head.text}"""" })
      sb ++= ")"
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

  def translate(is: InputStream) : StringBuilder = {
    val factory = new SAXFactoryImpl
    factory.setNamespaceAware(false)
    val loader : XMLLoader[Elem] = XML.withSAXParser(factory.newSAXParser())
    val root : scala.xml.Elem = loader.load(is)
    val body : NodeSeq = root \\ "body"
    process(body, 0)
  }

  def translate(is: InputStream, os: OutputStream)  : Unit =
    os.write( translate(is).toString.getBytes )
}
