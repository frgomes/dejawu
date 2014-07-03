package org.dejawu

import org.scalajs.dom

import scalatags.Text._
import scalatags.Text.all._
import scalatags.Escaping
import scalatags.Platform
import scalatags.generic
import scalatags.text

object DojoTags {
  val `data-dojo-id`    : Attr = "data-dojo-id".attr
  val `data-dojo-type`  : Attr = "data-dojo-type".attr
  val `data-dojo-props` : Attr = "data-dojo-props".attr
}


object dojo {


  object dijit {
    val select   = "select".dtag[form.SelectElement]("dijit/form/Select")
    val combobox = "input".dtag[form.ComboBoxElement]("dijit/form/ComboBox")

    object form {
      class SelectElement extends dom.HTMLSelectElement
      class ComboBoxElement extends dom.HTMLSelectElement
    }
  }

  object store {
    val memory   = "div".dtag[MemoryElement]("dojo/store/Memory")

    class MemoryElement extends dom.HTMLDivElement
  }

  implicit class DojoConversions(s: String) {
    def dtag[T <: Platform.Base](dtype: String) = {
      if (!Escaping.validTag(s))
        throw new IllegalArgumentException(s"Illegal tag name: $s is not a valid XML tag name")
      TypedTag(s, List(List(DojoTags.`data-dojo-type` := dtype)), false)
      //makeAbstractTypedTag[T](s, List(List(`data-dojo-type` := dtype)), false)
    }
    //private def makeAbstractTypedTag[T <: Platform.Base](tag: String, modifiers: List[Seq[Modifier[Builder]]], void: Boolean): TypedTag[T] = {
    //  TypedTag(tag, modifiers, void)
    //}
  }
}
