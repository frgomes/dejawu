package org.dejawu

import org.scalajs.dom
import scala.scalajs.{js => js}
import scala.scalajs.js.Dynamic.{ global => g }
import scala.scalajs.js.annotation.JSExport

import scala.collection.{SortedMap, mutable}

import scalatags.all._
import scalatags._

import dojo.`data-dojo-id`
import dojo.`data-dojo-props`


@JSExport
object ScalaJSExample {

  @JSExport
  def main(args: Array[String]): Unit = {
    val container = g.document.getElementById("container")
    container.innerHTML = render().toString()
  }

  private def render() =
    div(
      dojo.dijit.select(name := "select1")(
        option(value := "TN")("Tennessee"),
        option(value := "VA")("Virginia"),
        option(value := "WA")("Washington"),
        option(value := "FL")("Florida"),
        option(value := "CA")("California")),
      dojo.store.memory(
        `data-dojo-id`    := "stateStore",
        `data-dojo-props` := """data: [
                                    |{ id: 'EG', name:'Egypt'    },
                                    |{ id: 'KE', name:'Kenya'    },
                                    |{ id: 'SD', name:'Sudan'    },
                                    |{ id: 'CN', name:'China'    },
                                    |{ id: 'IN', name:'India'    },
                                    |{ id: 'RU', name:'Russia'   },
                                    |{ id: 'MN', name:'Mongolia' },
                                    |{ id: 'DE', name:'Germany'  },
                                    |{ id: 'FR', name:'France'   },
                                    |{ id: 'ES', name:'Spain'    },
                                    |{ id: 'IT', name:'Italy'    }
                                    |]""".stripMargin),
      dojo.dijit.combobox(
        name := "combobox1", value := "Egypt",
        `data-dojo-props` := "store:stateStore, searchAttr:'name'")
    )
}


object dojo {

  val `data-dojo-id`    : Attr = "data-dojo-id".attr
  val `data-dojo-type`  : Attr = "data-dojo-type".attr
  val `data-dojo-props` : Attr = "data-dojo-props".attr

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
    def dtag[T](dtype: String) = {
      if (!Escaping.validTag(s))
        throw new IllegalArgumentException(
          s"Illegal tag name: $s is not a valid XML tag name"
        )
      HtmlTag[T](s, Nil, SortedMap(`data-dojo-type`.name -> dtype))
    }
  }

}
