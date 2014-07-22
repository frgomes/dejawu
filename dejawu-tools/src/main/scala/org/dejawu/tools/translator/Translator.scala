package org.dejawu.tools.translator


case class CmdLine(input: String = null, output: String = null)


class CLI(args : Array[String]) {
  private val parser =
    new scopt.OptionParser[CmdLine]("translator") {
      head("""usage: translator [<input>] [<output>]
           |synopsis:
           |  Translate a HTML file to ScalaJS, employing Scalatags and Dejawu.
           |  More info on ScalaJS:      https://github.com/scala-js/scala-js
           |               Scalatags:    https://github.com/lihaoyi/scalatags
           |               Dejawu:       https://github.com/frgomes/dejawu
           |options:""".stripMargin)
      arg[String]("<input>")
        .optional()
        .action { (o, c) => c.copy( input = o ) }
        .text("""Input .html file or "-" (without quotes) for stdin""")
      arg[String]("<output>")
        .optional()
        .action { (o, c) => c.copy( output = o ) }
        .text("""Output .scala file or "-" (without quotes) for stdout""")
    }

  def parse : Option[CmdLine] = parser.parse(args, CmdLine())
}


object Translator {
  def main(args: Array[String]) {
    val cmd  = new CLI(args).parse
    val tool = new Translator
    if (cmd.isDefined)
      tool.translate( Some(cmd.get.input), Some(cmd.get.output) )
  }
}


class Translator {
  import java.io.{InputStream,OutputStream}
  import scala.xml.{Elem, XML, NodeSeq, Node}
  import scala.xml.factory.XMLLoader
  import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl

  def escape(s: String) = if(s.indexOf("-") == -1) s else s"`$s`"

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
        case _ : Option[Seq[Node]] => djtype.get.head.text
      }
    sb ++= "  " * level
    sb ++= tag
    sb ++= "("
    attrs
      .filter(attr => attr.key != "data-dojo-type")
      .foldLeft(new StringBuilder)((sb, attr) => {
        if(sb.length > 0) sb ++= ", "
        sb ++= escape(attr.key)
        sb ++= " := "
        sb ++= attr.value.head.text })
    sb ++= ")"
    if(node.child.length > 0) {
      sb ++= "(\n"
      process(node.child, level+1)
      sb ++= "\n"
      sb ++= "  " * level
      sb ++= ")"
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

  def translate(input: Option[String], output: Option[String]) : Unit = {
    import java.io.{FileInputStream,PrintStream}
    val is : InputStream = input match {
      case Some("-") => System.in
      case Some(_)   => new FileInputStream(input.get)
      case _         => System.in
    }
    val os : PrintStream = output match {
      case Some("-") => System.out
      case Some(_)   => new PrintStream(output.get)
      case _         => System.out
    }
    translate(is, os)
  }
}
