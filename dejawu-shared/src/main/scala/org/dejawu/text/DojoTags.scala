package org.dejawu.text

import scalatags.generic._
import scalatags.text._

import org.dejawu.generic


trait DojoTags extends generic.DojoTags[Builder, String, String]
                  with generic.DojoAttrs[Builder, String, String] {
  object dijit {
    val calendar         = "div" .tag(List(List(`data-dojo-type` := "dijit/Calendar"        )))
    val calendarlite     = "div" .tag(List(List(`data-dojo-type` := "dijit/CalendarLite"    )))
    val colorpalette     = "div" .tag(List(List(`data-dojo-type` := "dijit/ColorPalette"    )))
    val declaration      = "div" .tag(List(List(`data-dojo-type` := "dijit/Declaration"     )))
    val dialog           = "div" .tag(List(List(`data-dojo-type` := "dijit/Dialog"          )))
    val editor           = "div" .tag(List(List(`data-dojo-type` := "dijit/Editor"          )))
    val fieldset         = "div" .tag(List(List(`data-dojo-type` := "dijit/Fieldset"        )))
    val inlineeditbox    = "div" .tag(List(List(`data-dojo-type` := "dijit/InlineEditBox"   )))
    val progressbar      = "div" .tag(List(List(`data-dojo-type` := "dijit/ProgressBar"     )))
    val titlepane        = "div" .tag(List(List(`data-dojo-type` := "dijit/TitlePane"       )))
    val toolbar          = "div" .tag(List(List(`data-dojo-type` := "dijit/Toolbar"         )))
    val toolbarseparator = "span".tag(List(List(`data-dojo-type` := "dijit/ToolbarSeparator")))
    val tooltip          = "div" .tag(List(List(`data-dojo-type` := "dijit/Tooltip"         )))
    val tooltipdialog    = "div" .tag(List(List(`data-dojo-type` := "dijit/TooltipDialog"   )))
    object layout {
      val bordercontainer   = "div"     .tag(List(List(`data-dojo-type` := "dijit/layout/BorderContainer")))
    }
    object form {
      val button            = "button"  .tag(List(List(`data-dojo-type` := "dijit/form/Button"           )))
      val checkbox          = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/CheckBox"         )))
      val combobox          = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/ComboBox"         )))
      val combobutton       = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/ComboButton"      )))
      val currencytextbox   = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/CurrencyTextBox"  )))
      val datetextbox       = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/DateTextBox"      )))
      val dropdownbutton    = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/DropDownButton"   )))
      val filteringselect   = "select"  .tag(List(List(`data-dojo-type` := "dijit/form/FilteringSelect"  )))
      val form              = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/Form"             )))
      val mappedtextbox     = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/MappedTextBox"    )))
      val multiselect       = "select"  .tag(List(List(`data-dojo-type` := "dijit/form/MultiSelect"      )))
      val numberspinner     = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/NumberSpinner"    )))
      val numbertextbox     = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/NumberTextBox"    )))
      val radiobutton       = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/RadioButton"      )))
      val rangeboundtextbox = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/RangeBoundTextBox")))
      val select            = "select"  .tag(List(List(`data-dojo-type` := "dijit/form/Select"           )))
      val simpletextarea    = "textarea".tag(List(List(`data-dojo-type` := "dijit/form/SimpleTextarea"   )))
      val textbox           = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/TextBox"          )))
      val textarea          = "textarea".tag(List(List(`data-dojo-type` := "dijit/form/Textarea"         )))
      val timetextbox       = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/TimeTextBox"      )))
      val togglebutton      = "button"  .tag(List(List(`data-dojo-type` := "dijit/form/ToggleButton"     )))
      val validationtextbox = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/ValidationTextBox")))
    }
  }
  object dojo {
    object store {
      val memory = "div".tag(List(List(`data-dojo-type` := "dojo/store/Memory")))
    }
  }
}
