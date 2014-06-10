package org.dejawu

import org.scalajs.dom
import scala.scalajs.{js => js}
import scala.scalajs.js.Dynamic.{ global => g }
import scala.scalajs.js.annotation.JSExport

import scalatags.all._
import scalatags._


@JSExport
object ScalaJSExample {

  @JSExport
  def main(args: Array[String]): Unit = {
    val container = g.document.getElementById("container")
    container.innerHTML = render().toString()
  }

  private def render() =
    dijit.select(name := "select1")(
      option(value := "TN")("Tennessee"),
      option(value := "VA")("Virginia"),
      option(value := "WA")("Washington"),
      option(value := "FL")("Florida"),
      option(value := "CA")("California"))

}

object dijit {

  val select = "select".tag[Select].apply(
    "data-dojo-type".attr := "dijit/form/Select"
  )

  class Select extends dom.HTMLDivElement
}




/*
  <div data-dojo-id="stateStore"
       data-dojo-type="dojo/store/Memory"
       data-dojo-props="data: [
                            { id: 'EG', name:'Egypt'    },
                            { id: 'KE', name:'Kenya'    },
                            { id: 'SD', name:'Sudan'    },
                            { id: 'CN', name:'China'    },
                            { id: 'IN', name:'India'    },
                            { id: 'RU', name:'Russia'   },
                            { id: 'MN', name:'Mongolia' },
                            { id: 'DE', name:'Germany'  },
                            { id: 'FR', name:'France'   },
                            { id: 'ES', name:'Spain'    },
                            { id: 'IT', name:'Italy'    }

*/
