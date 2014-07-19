package org.dejawu

import scala.scalajs.js
import js.annotation.JSExport
import js.Dynamic.{ global => g }

import scalatags.Text.all._
import org.dejawu.DojoText.all._


object ScalaJSExample extends js.JSApp {

  def main() = {
    val container = g.document.getElementById("container")
    container.innerHTML = render().toString()
  }

  private def render() =
    div(
      dijit.form.select(name := "select1")(
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
      dijit.form.combobox(
        name := "combobox1", value := "Egypt",
        `data-dojo-props` := "store:stateStore, searchAttr:'name'")
    )
}
