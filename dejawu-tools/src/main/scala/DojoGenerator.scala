package org.dejawu.tools

import java.io.{InputStream,FileInputStream,File}
import java.net.URL

import scala.io.Source
import scala.collection.mutable
import scala.collection.immutable


object DojoGenerator {

  def main(args: Array[String]) {
    //TODO:: val gen = new DojoGenerator
  }
}


/**
  * An Entry obtained from a specification line such as:
  *     dojo.store.Memory = div ; dom.HTMLDivElement
  * becomes:
  *    widget="dojo.store.Memory"
  *    path="dojo.store"
  *    level=2
  *    element="MemoryElement"
  *    tag="div"
  *    htmlElement="dom.HTMLDivElement"
  */
case class Entry(widget: String, htmlTag: String, htmlElement: String) {
  val (tag, path, level, element) = {
    val parts = widget.split('.')
    val path    : String = parts.slice(0, parts.length-1).mkString(".")
    val level   : Int    = parts.length-1
    val tag     : String = parts(parts.length-1).toLowerCase
    val element : String = parts(parts.length-1) + "Element"
    (tag, path, level, element) }
}


class DojoGenerator {

  def parse(url: URL) : List[Entry] = parse(url.openStream)

  def parse(f: File) : List[Entry] = parse(new FileInputStream(f))

  def parse(is: InputStream) : List[Entry] = parse(Source.fromInputStream(is))

  def parse(bs: Source) : List[Entry] = parse(bs.getLines.toList)

  def parse(lines: List[String]) : List[Entry] =
    lines.map(line => tokens(line))
      .filter(o => o.isDefined)
      .map(o => o.get)

  def tokens(line : String) : Option[Entry] = {
    val widget = line.trim().split("=").head.trim
    val tail   = line.trim().split("=").tail
    if(widget.length > 0 &&
      !widget.startsWith("#") && !widget.startsWith(";") && !widget.startsWith("//") &&
      tail.length == 1) {
      val parts = tail(0).split(",")
      if(parts.length == 2) {
        val tag  = parts(0).trim
        val elem = parts(1).trim
        if(tag.length > 0 && elem.length > 0)
          return Option(Entry(widget, tag, elem))
      }
    }
    None
  }

  def prologue() : String = """
    |package org.dejawu
    |
    |import org.scalajs.dom
    |
    |import scalatags.Text._
    |import scalatags.Text.all._
    |import scalatags.Escaping
    |import scalatags.Platform
    |import scalatags.generic
    |import scalatags.text
    |
    |object DojoTags {
    |  val `data-dojo-id`    : Attr = "data-dojo-id".attr
    |  val `data-dojo-type`  : Attr = "data-dojo-type".attr
    |  val `data-dojo-props` : Attr = "data-dojo-props".attr
    |}
    |
    |""".stripMargin

  def epilogue : String = """
    |  implicit class DojoConversions(s: String) {
    |    def dtag[T <: Platform.Base](dtype: String) = {
    |      if (!Escaping.validTag(s))
    |        throw new IllegalArgumentException(s"Illegal tag name: $s is not a valid XML tag name")
    |      TypedTag(s, List(List(DojoTags.`data-dojo-type` := dtype)), false)
    |      //makeAbstractTypedTag[T](s, List(List(`data-dojo-type` := dtype)), false)
    |    }
    |    //private def makeAbstractTypedTag[T <: Platform.Base](tag: String, modifiers: List[Seq[Modifier[Builder]]], void: Boolean): TypedTag[T] = {
    |    //  TypedTag(tag, modifiers, void)
    |    //}
    |  }
    |}""".stripMargin


  def scopeChange(prev: String, curr: String) : String = {
    val builder = new StringBuilder
    if (prev == curr) "" else {
      val pparts = Option(prev).getOrElse("").+('.').split('.')
      val cparts = Option(curr).getOrElse("").+('.').split('.')
      var min = 0
      while (min < pparts.length && min < cparts.length && pparts(min) == cparts(min)) min += 1
      for(count <- pparts.length until min by -1) builder ++= ("  "*(count-1)) + "}\n"
      for(count <- min until cparts.length) builder ++= ("  "*count) + s"object ${cparts(count)} {\n"
    }
    builder.toString
  }

  /**
    * Generates declarations for a Dojo tag and for a Dojo element.
    * Example:
    * {{{
    *   val memory   = "div".dtag[MemoryElement]("dojo/store/Memory")
    *   val combobox = "input".dtag[ComboBoxElement]("dojo/store/Memory")
    * 
    *   class MemoryElement extends dom.HTMLDivElement
    * }}}
    */
  def declareElement(entry: Entry) : String = {
    val builder  = new StringBuilder
    builder ++= "  " * entry.level
    builder ++= s"""  val ${entry.tag} = "${entry.htmlTag}".dtag[${entry.element}]("${entry.widget}")\n"""
    builder ++= "  " * entry.level
    builder ++= s"""  class ${entry.element} extends ${entry.htmlElement}\n"""
    builder.toString
  }


  def body(entries: List[Entry]) : String = {
    var prev: String = ""
    val builder = new StringBuilder
    entries.foldLeft(immutable.TreeMap[String,Entry]())((map, entry) => map.updated(entry.widget, entry))
      .foreach { case (key, entry) => {
        builder ++= scopeChange(prev, entry.path)
        builder ++= declareElement(entry)
        prev = entry.path
      }
    }
    builder ++= scopeChange(prev, "")
    builder.toString
  }

  def generate(entries: List[Entry]) : String =
    prologue + body(entries) + epilogue

}
