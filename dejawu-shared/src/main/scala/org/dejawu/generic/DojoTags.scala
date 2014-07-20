package org.dejawu.generic

import org.scalajs.dom
import scalatags.generic._


trait DojoTags[Builder, Output <: FragT, FragT] extends Util[Builder, Output, FragT] {
  trait dijit {
    val calendar         : TypedTag[Builder, Output, FragT]
    val calendarlite     : TypedTag[Builder, Output, FragT]
    val colorpalette     : TypedTag[Builder, Output, FragT]
    val declaration      : TypedTag[Builder, Output, FragT]
    val dialog           : TypedTag[Builder, Output, FragT]
    val editor           : TypedTag[Builder, Output, FragT]
    val fieldset         : TypedTag[Builder, Output, FragT]
    val inlineeditbox    : TypedTag[Builder, Output, FragT]
    val progressbar      : TypedTag[Builder, Output, FragT]
    val titlepane        : TypedTag[Builder, Output, FragT]
    val toolbar          : TypedTag[Builder, Output, FragT]
    val toolbarseparator : TypedTag[Builder, Output, FragT]
    val tooltip          : TypedTag[Builder, Output, FragT]
    val tooltipdialog    : TypedTag[Builder, Output, FragT]
    trait layout {
      val bordercontainer : TypedTag[Builder, Output, FragT]
    }
    trait form {
      val button            : TypedTag[Builder, Output, FragT]
      val checkbox          : TypedTag[Builder, Output, FragT]
      val combobox          : TypedTag[Builder, Output, FragT]
      val combobutton       : TypedTag[Builder, Output, FragT]
      val currencytextbox   : TypedTag[Builder, Output, FragT]
      val datetextbox       : TypedTag[Builder, Output, FragT]
      val dropdownbutton    : TypedTag[Builder, Output, FragT]
      val filteringselect   : TypedTag[Builder, Output, FragT]
      val form              : TypedTag[Builder, Output, FragT]
      val mappedtextbox     : TypedTag[Builder, Output, FragT]
      val multiselect       : TypedTag[Builder, Output, FragT]
      val numberspinner     : TypedTag[Builder, Output, FragT]
      val numbertextbox     : TypedTag[Builder, Output, FragT]
      val radiobutton       : TypedTag[Builder, Output, FragT]
      val rangeboundtextbox : TypedTag[Builder, Output, FragT]
      val select            : TypedTag[Builder, Output, FragT]
      val simpletextarea    : TypedTag[Builder, Output, FragT]
      val textbox           : TypedTag[Builder, Output, FragT]
      val textarea          : TypedTag[Builder, Output, FragT]
      val timetextbox       : TypedTag[Builder, Output, FragT]
      val togglebutton      : TypedTag[Builder, Output, FragT]
      val validationtextbox : TypedTag[Builder, Output, FragT]
    }
  }
  trait dojo {
    trait store {
      val memory : TypedTag[Builder, Output, FragT]
    }
  }
}
