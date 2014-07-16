package org.dejawu


////////////////////////////////////////////////////////////////////////////
//
// This class mimics what scalatags.Text does
//
// It looks just wrong to me.
// I guess it should be a better way to build functionality on top
// of scalatags without this dirty copy/paste of what scalatags does.
//
// But it compiles.
//
////////////////////////////////////////////////////////////////////////////

object DojoText extends scalatags.generic.Bundle[scalatags.text.Builder, String, String]
                   with scalatags.generic.Aliases[scalatags.text.Builder, String, String] {

  object attrs     extends scalatags.Text.Cap with scalatags.Text.Attrs
  object tags      extends scalatags.Text.Cap with scalatags.text.Tags
  object tags2     extends scalatags.Text.Cap with scalatags.text.Tags2
  object styles    extends scalatags.Text.Cap with scalatags.Text.Styles
  object styles2   extends scalatags.Text.Cap with scalatags.generic.Styles2[scalatags.text.Builder, String, String]
  object svgTags   extends scalatags.Text.Cap with scalatags.text.SvgTags
  object svgStyles extends scalatags.Text.Cap with scalatags.Text.SvgStyles


  object implicits extends scalatags.Text.Aggregate

  object all
      extends scalatags.Text.Cap
      with scalatags.Text.Attrs
      with scalatags.Text.Styles
      with scalatags.text.Tags
      with scalatags.DataConverters
      with scalatags.Text.Aggregate
              with org.dejawu.generic.DojoAttrs[scalatags.text.Builder, String, String]
              with org.dejawu.text.DojoTags


  object short
      extends scalatags.Text.Cap
      with scalatags.generic.Util[scalatags.text.Builder, String, String]
      with scalatags.DataConverters
      with scalatags.Text.Aggregate
              with org.dejawu.generic.DojoAttrs[scalatags.text.Builder, String, String]
              with org.dejawu.text.DojoTags
      with scalatags.Text.AbstractShort {
    object * extends scalatags.Text.Cap with scalatags.Text.Attrs with scalatags.Text.Styles
  }

  object StringFrag extends scalatags.Companion[StringFrag]
  object RawFrag    extends scalatags.Companion[RawFrag]

  // FIXME: (THIS IS DIRTY) :: should not copy/paste from scalatags.Text
  case class StringFrag(v: String) extends scalatags.text.Frag{
    def render = {
      val strb = new StringBuilder()
      writeTo(strb)
      strb.toString()
    }
    def writeTo(strb: StringBuilder) = scalatags.Escaping.escape(v, strb)
  }

  // FIXME: (THIS IS DIRTY) :: should not copy/paste from scalatags.Text
  case class RawFrag(v: String) extends scalatags.text.Frag {
    def render = v
    def writeTo(strb: StringBuilder) = strb ++= v
  }

}




//
// HERE STARTS application code which relies on scalatags and dejawu
//


import scala.scalajs.js
import js.annotation.JSExport
import js.Dynamic.{ global => g }

//import scalatags.Text.all._
import DojoText.all._


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
        djid    := "stateStore",
        djprops := """data: [
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
        djprops := "store:stateStore, searchAttr:'name'")
    )
}
