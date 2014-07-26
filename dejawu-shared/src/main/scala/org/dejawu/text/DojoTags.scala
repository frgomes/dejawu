package org.dejawu.text

import scalatags.generic._
import scalatags.text._

import org.dejawu.generic


trait DojoTags extends generic.DojoTags[Builder, String, String]
                  with generic.DojoAttrs[Builder, String, String] {
  object dijit {
    val Calendar         = "div" .tag(List(List(`data-dojo-type` := "dijit/Calendar"        )))
    val CalendarLite     = "div" .tag(List(List(`data-dojo-type` := "dijit/CalendarLite"    )))
    val ColorPalette     = "div" .tag(List(List(`data-dojo-type` := "dijit/ColorPalette"    )))
    val Declaration      = "div" .tag(List(List(`data-dojo-type` := "dijit/Declaration"     )))
    val Dialog           = "div" .tag(List(List(`data-dojo-type` := "dijit/Dialog"          )))
    val Editor           = "div" .tag(List(List(`data-dojo-type` := "dijit/Editor"          )))
    val Fieldset         = "div" .tag(List(List(`data-dojo-type` := "dijit/Fieldset"        )))
    val InlineEditBox    = "div" .tag(List(List(`data-dojo-type` := "dijit/InlineEditBox"   )))
    val Menu             = "div" .tag(List(List(`data-dojo-type` := "dijit/Menu"            )))
    val MenuItem         = "div" .tag(List(List(`data-dojo-type` := "dijit/MenuItem"        )))
    val MenuSeparator    = "div" .tag(List(List(`data-dojo-type` := "dijit/MenuSeparator"   )))
    val MenuBar          = "div" .tag(List(List(`data-dojo-type` := "dijit/MenuBar"         )))
    val PopupMenuItem    = "div" .tag(List(List(`data-dojo-type` := "dijit/PopupMenuItem"   )))
    val PopupMenuBarItem = "div" .tag(List(List(`data-dojo-type` := "dijit/PopupMenuBarItem")))
    val Progressbar      = "div" .tag(List(List(`data-dojo-type` := "dijit/ProgressBar"     )))
    val RadioMenuItem    = "div" .tag(List(List(`data-dojo-type` := "dijit/RadioMenuItem"   )))
    val Titlepane        = "div" .tag(List(List(`data-dojo-type` := "dijit/TitlePane"       )))
    val Toolbar          = "div" .tag(List(List(`data-dojo-type` := "dijit/Toolbar"         )))
    val Toolbarseparator = "span".tag(List(List(`data-dojo-type` := "dijit/ToolbarSeparator")))
    val Tooltip          = "div" .tag(List(List(`data-dojo-type` := "dijit/Tooltip"         )))
    val TooltipDialog    = "div" .tag(List(List(`data-dojo-type` := "dijit/TooltipDialog"   )))
    object layout {
      var AccordionContainer = "div"     .tag(List(List(`data-dojo-type` := "dijit/layout/AccordionContainer")))
      val BorderContainer    = "div"     .tag(List(List(`data-dojo-type` := "dijit/layout/BorderContainer"   )))
      val ContentPane        = "div"     .tag(List(List(`data-dojo-type` := "dijit/layout/ContentPane"       )))
    }
    object form {
      val Button            = "button"  .tag(List(List(`data-dojo-type` := "dijit/form/Button"           )))
      val CheckBox          = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/CheckBox"         )))
      val ComboBox          = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/ComboBox"         )))
      val ComboButton       = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/ComboButton"      )))
      val CurrencyTextBox   = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/CurrencyTextBox"  )))
      val DateTextBox       = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/DateTextBox"      )))
      val DropDownButton    = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/DropDownButton"   )))
      val FilteringSelect   = "select"  .tag(List(List(`data-dojo-type` := "dijit/form/FilteringSelect"  )))
      val Form              = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/Form"             )))
      val MappedTextBox     = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/MappedTextBox"    )))
      val MultiSelect       = "select"  .tag(List(List(`data-dojo-type` := "dijit/form/MultiSelect"      )))
      val NumberSpinner     = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/NumberSpinner"    )))
      val NumberTextBox     = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/NumberTextBox"    )))
      val RadioButton       = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/RadioButton"      )))
      val RangeboundTextBox = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/RangeBoundTextBox")))
      val Select            = "select"  .tag(List(List(`data-dojo-type` := "dijit/form/Select"           )))
      val SimpleTextArea    = "textarea".tag(List(List(`data-dojo-type` := "dijit/form/SimpleTextArea"   )))
      val TextBox           = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/TextBox"          )))
      val TextArea          = "textarea".tag(List(List(`data-dojo-type` := "dijit/form/TextArea"         )))
      val TimeTextBox       = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/TimeTextBox"      )))
      val ToggleButton      = "button"  .tag(List(List(`data-dojo-type` := "dijit/form/ToggleButton"     )))
      val ValidationTextBox = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/ValidationTextBox")))
    }
  }
  object dojo {
    object store {
      val Memory = "div".tag(List(List(`data-dojo-type` := "dojo/store/Memory")))
    }
  }
}
