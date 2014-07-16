package org.dejawu.text

import scalatags.generic._
import scalatags.text._

import org.dejawu.generic


trait DojoTags extends generic.DojoTags[Builder, String, String]
                  with generic.DojoAttrs[Builder, String, String] {
  object dijit {
    val calendar         = "div" .tag(List(List(djtype := "dijit.Calendar"        )))
    val calendarlite     = "div" .tag(List(List(djtype := "dijit.CalendarLite"    )))
    val colorpalette     = "div" .tag(List(List(djtype := "dijit.ColorPalette"    )))
    val declaration      = "div" .tag(List(List(djtype := "dijit.Declaration"     )))
    val dialog           = "div" .tag(List(List(djtype := "dijit.Dialog"          )))
    val editor           = "div" .tag(List(List(djtype := "dijit.Editor"          )))
    val fieldset         = "div" .tag(List(List(djtype := "dijit.Fieldset"        )))
    val inlineeditbox    = "div" .tag(List(List(djtype := "dijit.InlineEditBox"   )))
    val progressbar      = "div" .tag(List(List(djtype := "dijit.ProgressBar"     )))
    val titlepane        = "div" .tag(List(List(djtype := "dijit.TitlePane"       )))
    val toolbar          = "div" .tag(List(List(djtype := "dijit.Toolbar"         )))
    val toolbarseparator = "span".tag(List(List(djtype := "dijit.ToolbarSeparator")))
    val tooltip          = "div" .tag(List(List(djtype := "dijit.Tooltip"         )))
    val tooltipdialog    = "div" .tag(List(List(djtype := "dijit.TooltipDialog"   )))
    object form {
      val button            = "button"  .tag(List(List(djtype := "dijit.form.Button"           )))
      val checkbox          = "input"   .tag(List(List(djtype := "dijit.form.CheckBox"         )))
      val combobox          = "input"   .tag(List(List(djtype := "dijit.form.ComboBox"         )))
      val combobutton       = "div"     .tag(List(List(djtype := "dijit.form.ComboButton"      )))
      val currencytextbox   = "input"   .tag(List(List(djtype := "dijit.form.CurrencyTextBox"  )))
      val datetextbox       = "input"   .tag(List(List(djtype := "dijit.form.DateTextBox"      )))
      val dropdownbutton    = "div"     .tag(List(List(djtype := "dijit.form.DropDownButton"   )))
      val filteringselect   = "select"  .tag(List(List(djtype := "dijit.form.FilteringSelect"  )))
      val form              = "div"     .tag(List(List(djtype := "dijit.form.Form"             )))
      val mappedtextbox     = "input"   .tag(List(List(djtype := "dijit.form.MappedTextBox"    )))
      val multiselect       = "select"  .tag(List(List(djtype := "dijit.form.MultiSelect"      )))
      val numberspinner     = "input"   .tag(List(List(djtype := "dijit.form.NumberSpinner"    )))
      val numbertextbox     = "input"   .tag(List(List(djtype := "dijit.form.NumberTextBox"    )))
      val radiobutton       = "input"   .tag(List(List(djtype := "dijit.form.RadioButton"      )))
      val rangeboundtextbox = "input"   .tag(List(List(djtype := "dijit.form.RangeBoundTextBox")))
      val select            = "select"  .tag(List(List(djtype := "dijit.form.Select"           )))
      val simpletextarea    = "textarea".tag(List(List(djtype := "dijit.form.SimpleTextarea"   )))
      val textbox           = "input"   .tag(List(List(djtype := "dijit.form.TextBox"          )))
      val textarea          = "textarea".tag(List(List(djtype := "dijit.form.Textarea"         )))
      val timetextbox       = "input"   .tag(List(List(djtype := "dijit.form.TimeTextBox"      )))
      val togglebutton      = "button"  .tag(List(List(djtype := "dijit.form.ToggleButton"     )))
      val validationtextbox = "input"   .tag(List(List(djtype := "dijit.form.ValidationTextBox")))
    }
  }
  object dojo {
    object store {
      val memory = "div".tag(List(List(djtype := "dojo.store.Memory")))
    }
  }
}
