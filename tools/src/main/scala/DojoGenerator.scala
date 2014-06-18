

// This is exploratory ideas at this point. It's not even expected to compile

object DojoGenerator {

  def main(args: Array[String]) {
    val gen = new DojoGenerator()
  }
}


// dojo.store.Memory = div ; dom.HTMLDivElement
case class Entry(widget: String, tag: String, element: String)

class DojoGenerator(val is: InputStream) {
  private val widgets = HashMap[String, Entry]()

  def parse : List[Entry] = {
    val input = Source.fromInputStream(is)
    val lines = input.getLines.collect
    for (line <- lines) {
      val (widget, tail) = line.trim().split("=")
      if (tail != nil) {
        val (tag, element) = tail.split(",")
        if (element != Nil)
          entries + (widget, Entry(widget, tag, element)
      }
    }
    result
  }

  def prologue() : String = """
    |object dojo {
    | 
    |  val `data-dojo-id`    : Attr = "data-dojo-id".attr
    |  val `data-dojo-type`  : Attr = "data-dojo-type".attr
    |  val `data-dojo-props` : Attr = "data-dojo-props".attr
    | 
    |""".stripMargin

  def epilogue : String = """
    | 
    |  implicit class DojoConversions(s: String) {
    |    def dtag[T](dtype: String) = {
    |      if (!Escaping.validTag(s))
    |        throw new IllegalArgumentException(
    |          s"Illegal tag name: $s is not a valid XML tag name"
    |        )
    |      HtmlTag[T](s, Nil, SortedMap(`data-dojo-type`.name -> dtype))
    |    }
    |  }
    |}""".stripMargin


   def body(entry: Entry) : String = {
     val name     = entry.widget_.split("/").last
     val dojoTag  = name.lowercase
     val dojoElem = name + "Element"
     //  val memory   = "div".dtag[MemoryElement]("dojo/store/Memory")
     //  class MemoryElement extends dom.HTMLDivElement
     s"""      val ${dojoTag} = "${entry.tag}.dtag[${dojoElem}]("${entry.widget}")
        |      class ${dojoElem} extends ${entry.element}
        |
        |""".stripMargin
   }


}
