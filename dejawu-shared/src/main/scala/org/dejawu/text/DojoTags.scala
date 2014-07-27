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
    val ProgressBar      = "div" .tag(List(List(`data-dojo-type` := "dijit/ProgressBar"     )))
    val RadioMenuItem    = "div" .tag(List(List(`data-dojo-type` := "dijit/RadioMenuItem"   )))
    val TitlePane        = "div" .tag(List(List(`data-dojo-type` := "dijit/TitlePane"       )))
    val Toolbar          = "div" .tag(List(List(`data-dojo-type` := "dijit/Toolbar"         )))
    val Toolbarseparator = "span".tag(List(List(`data-dojo-type` := "dijit/ToolbarSeparator")))
    val Tooltip          = "div" .tag(List(List(`data-dojo-type` := "dijit/Tooltip"         )))
    val TooltipDialog    = "div" .tag(List(List(`data-dojo-type` := "dijit/TooltipDialog"   )))
    val Tree             = "div" .tag(List(List(`data-dojo-type` := "dijit/Tree"            )))
    object layout {
      var AccordionContainer = "div"     .tag(List(List(`data-dojo-type` := "dijit/layout/AccordionContainer")))
      var AccordionPane      = "div"     .tag(List(List(`data-dojo-type` := "dijit/layout/AccordionPane"     )))
      val BorderContainer    = "div"     .tag(List(List(`data-dojo-type` := "dijit/layout/BorderContainer"   )))
      val ContentPane        = "div"     .tag(List(List(`data-dojo-type` := "dijit/layout/ContentPane"       )))
      val TabContainer       = "div"     .tag(List(List(`data-dojo-type` := "dijit/layout/TabContainer"      )))
    }
    object form {
      val Button               = "button"  .tag(List(List(`data-dojo-type` := "dijit/form/Button"               )))
      val CheckBox             = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/CheckBox"             )))
      val ComboBox             = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/ComboBox"             )))
      val ComboButton          = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/ComboButton"          )))
      val CurrencyTextBox      = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/CurrencyTextBox"      )))
      val DateTextBox          = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/DateTextBox"          )))
      val DropDownButton       = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/DropDownButton"       )))
      val FilteringSelect      = "select"  .tag(List(List(`data-dojo-type` := "dijit/form/FilteringSelect"      )))
      val Form                 = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/Form"                 )))
      val HorizontalRule       = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/HorizontalRule"       )))
      val HorizontalRuleLabels = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/HorizontalRuleLabels" )))
      val HorizontalSlider     = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/HorizontalSlider"     )))
      val MappedTextBox        = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/MappedTextBox"        )))
      val MultiSelect          = "select"  .tag(List(List(`data-dojo-type` := "dijit/form/MultiSelect"          )))
      val NumberSpinner        = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/NumberSpinner"        )))
      val NumberTextBox        = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/NumberTextBox"        )))
      val RadioButton          = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/RadioButton"          )))
      val RangeboundTextBox    = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/RangeBoundTextBox"    )))
      val Select               = "select"  .tag(List(List(`data-dojo-type` := "dijit/form/Select"               )))
      val SimpleTextarea       = "textarea".tag(List(List(`data-dojo-type` := "dijit/form/SimpleTextarea"       )))
      val TextBox              = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/TextBox"              )))
      val Textarea             = "textarea".tag(List(List(`data-dojo-type` := "dijit/form/Textarea"             )))
      val TimeTextBox          = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/TimeTextBox"          )))
      val ToggleButton         = "button"  .tag(List(List(`data-dojo-type` := "dijit/form/ToggleButton"         )))
      val ValidationTextBox    = "input"   .tag(List(List(`data-dojo-type` := "dijit/form/ValidationTextBox"    )))
      val VerticalRule         = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/VerticalRule"         )))
      val VerticalRuleLabels   = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/VerticalRuleLabels"   )))
      val VerticalSlider       = "div"     .tag(List(List(`data-dojo-type` := "dijit/form/VerticalSlider"       )))
    }
    object tree {
      val ObjectStoreModel     = "div"     .tag(List(List(`data-dojo-type` := "dijit/tree/ObjectStoreModel"     )))
    }
  }
  object dojo {
    val Query       = "div"     .tag(List(List(`data-dojo-type` := "dojo/Query"      )))
    object dnd {
      val Source    = "div"     .tag(List(List(`data-dojo-type` := "dojo/dnd/Source" )))
    }
    object store {
      val Memory = "div".tag(List(List(`data-dojo-type` := "dojo/store/Memory")))
    }
  }
}
