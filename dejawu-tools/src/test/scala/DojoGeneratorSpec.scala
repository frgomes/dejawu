package org.dejawu.tools

import java.io.InputStream
import org.scalatest._


class DojoGeneratorSpec extends FeatureSpec with GivenWhenThen {

  val tool = new DojoGenerator

  feature("Ability to split line into components") {
    scenario("an empty line") {
      val line = ""
      val entry : Option[Entry] = tool.tokens(line)
      assert(! entry.isDefined)
    }

    scenario("a valid line containing a full specification") {
      val line = "dojo.store.Memory=div,dom.HTMLDivElement"
      val entry : Option[Entry] = tool.tokens(line)
      assert(entry.isDefined)
      assert(entry.get.widget      === "dojo.store.Memory" )
      assert(entry.get.tag         === "memory" )
      assert(entry.get.path        === "dojo.store" )
      assert(entry.get.level       === 2 )
      assert(entry.get.element     === "MemoryElement" )
      assert(entry.get.htmlTag     === "div" )
      assert(entry.get.htmlElement === "dom.HTMLDivElement" )
    }

    scenario("a valid line, but comment out with '#'") {
      val line = "#dojo.store.Memory=div,dom.HTMLDivElement"
      val entry : Option[Entry] = tool.tokens(line)
      assert(! entry.isDefined)
    }

    scenario("a valid line, but comment out with ';'") {
      val line = ";dojo.store.Memory=div,dom.HTMLDivElement"
      val entry : Option[Entry] = tool.tokens(line)
        assert(! entry.isDefined)
    }

    scenario("a valid line, but comment out with '//'") {
      val line = "//dojo.store.Memory=div,dom.HTMLDivElement"
      val entry : Option[Entry] = tool.tokens(line)
      assert(! entry.isDefined)
    }

    scenario("invalid line: insufficient number of tokens") {
      val line = "//dojo.store.Memory=div"
      val entry : Option[Entry] = tool.tokens(line)
      assert(! entry.isDefined)
    }

    scenario("invalid line, excessive number of tokens") {
      val line = "//dojo.store.Memory=div,dom.HTMLDivElement,Woody Woodpecker"
      val entry : Option[Entry] = tool.tokens(line)
        assert(! entry.isDefined)
    }
  }

  feature("Ability to process several lines") {
    scenario("List[String] contains lines with unsorted contents") {
      val lines = List[String](
        "dojo.store.Memory=div,dom.HTMLDivElement",
        "",
        "",
        "#-- these are all comments",
        "#dojo.store.Memory=div,dom.HTMLDivElement",
        ";dojo.store.Memory=div,dom.HTMLDivElement",
        "//dojo.store.Memory=div,dom.HTMLDivElement",
        "",
        "#-- some more contents",
        "dijit.form.Select=select,dom.HTMLSelectElement",
        "dijit.form.ComboBox=input,dom.HTMLSelectElement",
        "",
        "#-- The End")
      val actual = tool.parse(lines)
      val expected = List(
        Entry("dojo.store.Memory",   "div",    "dom.HTMLDivElement"),
        Entry("dijit.form.Select",   "select", "dom.HTMLSelectElement"),
        Entry("dijit.form.ComboBox", "input",  "dom.HTMLSelectElement"))
      assert(actual === expected)
    }
  }

  feature("Ability to handle scopes") {

    scenario("Empty scopes") {
      assert(tool.scopeChange(null, null) === "")
      assert(tool.scopeChange("",   ""  ) === "")
    }

    scenario("Same scopes") {
      assert(tool.scopeChange("dojo.dijit", "dojo.dijit") === "")
    }

    scenario("Sibbling scopes") {
      assert(tool.scopeChange("dojo",            "dojox")           === "}\nobject dojox {\n")
      assert(tool.scopeChange("dojo.dijit",      "dojo.store")      === "  }\n  object store {\n")
      assert(tool.scopeChange("dojo.dijit.form", "dojo.dijit.fake") === "    }\n    object fake {\n")
    }

    scenario("Inner scopes") {
      assert(tool.scopeChange("dojo.dijit", "dojo.dijit.form") === "    object form {\n")
      assert(tool.scopeChange("dojo",       "dojo.dijit.form") === "  object dijit {\n    object form {\n")
      assert(tool.scopeChange("",           "dojo.dijit.form") === "object dojo {\n  object dijit {\n    object form {\n")
    }

    scenario("Outer scopes") {
      assert(tool.scopeChange("dojo.dijit.form", "dojo.dijit") === "    }\n")
      assert(tool.scopeChange("dojo.dijit.form", "dojo"      ) === "    }\n  }\n")
      assert(tool.scopeChange("dojo.dijit.form", ""          ) === "    }\n  }\n}\n")
    }

  }


  feature("Ability to produce output") {
    scenario("A single entry is passed as test") {
      assert( tool.generate(
        List(
          Entry("dojo.dijit.form.ComboBox", "input",  "dom.HTMLSelectElement"))) ===
        """
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
          |object dojo {
          |  object dijit {
          |    object form {
          |        val combobox = "input".dtag[ComboBoxElement]("dojo.dijit.form.ComboBox")
          |        class ComboBoxElement extends dom.HTMLSelectElement
          |    }
          |  }
          |}
          |
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
          |}""".stripMargin )
    }

    scenario("Several entries are passed as test") {
      assert( tool.generate(
        List(
          Entry("dojo.store.Memory",        "div",    "dom.HTMLDivElement"),
          Entry("dojo.dijit.form.Select",   "select", "dom.HTMLSelectElement"),
          Entry("dojo.dijit.form.ComboBox", "input",  "dom.HTMLSelectElement"))) ===
        """
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
          |object dojo {
          |  object dijit {
          |    object form {
          |        val combobox = "input".dtag[ComboBoxElement]("dojo.dijit.form.ComboBox")
          |        class ComboBoxElement extends dom.HTMLSelectElement
          |        val select = "select".dtag[SelectElement]("dojo.dijit.form.Select")
          |        class SelectElement extends dom.HTMLSelectElement
          |    }
          |  }
          |  object store {
          |      val memory = "div".dtag[MemoryElement]("dojo.store.Memory")
          |      class MemoryElement extends dom.HTMLDivElement
          |  }
          |}
          |
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
          |}""".stripMargin)
    }
  }

}
