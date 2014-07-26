package org.dejawu.generic

import org.scalajs.dom
import scalatags.generic._


trait DojoTags[Builder, Output <: FragT, FragT] extends Util[Builder, Output, FragT] {
  trait dijit {
    val Calendar         : TypedTag[Builder, Output, FragT]
    val CalendarLite     : TypedTag[Builder, Output, FragT]
    val ColorPalette     : TypedTag[Builder, Output, FragT]
    val Declaration      : TypedTag[Builder, Output, FragT]
    val Dialog           : TypedTag[Builder, Output, FragT]
    val Editor           : TypedTag[Builder, Output, FragT]
    val Fieldset         : TypedTag[Builder, Output, FragT]
    val InlineEditBox    : TypedTag[Builder, Output, FragT]
    val Menu             : TypedTag[Builder, Output, FragT]
    val MenuItem         : TypedTag[Builder, Output, FragT]
    val MenuSparator     : TypedTag[Builder, Output, FragT]
    val ProgressBar      : TypedTag[Builder, Output, FragT]
    val TitlePane        : TypedTag[Builder, Output, FragT]
    val Toolbar          : TypedTag[Builder, Output, FragT]
    val ToolbarSeparator : TypedTag[Builder, Output, FragT]
    val Tooltip          : TypedTag[Builder, Output, FragT]
    val TooltipDialog    : TypedTag[Builder, Output, FragT]
    trait layout {
      val Bordercontainer : TypedTag[Builder, Output, FragT]
    }
    trait form {
      val Button            : TypedTag[Builder, Output, FragT]
      val CheckBox          : TypedTag[Builder, Output, FragT]
      val ComboBox          : TypedTag[Builder, Output, FragT]
      val ComboButton       : TypedTag[Builder, Output, FragT]
      val CurrencyTextBox   : TypedTag[Builder, Output, FragT]
      val DateTextBox       : TypedTag[Builder, Output, FragT]
      val DropDownButton    : TypedTag[Builder, Output, FragT]
      val FilteringSelect   : TypedTag[Builder, Output, FragT]
      val Form              : TypedTag[Builder, Output, FragT]
      val MappedTextBox     : TypedTag[Builder, Output, FragT]
      val MultiSelect       : TypedTag[Builder, Output, FragT]
      val NumberSpinner     : TypedTag[Builder, Output, FragT]
      val NumberTextBox     : TypedTag[Builder, Output, FragT]
      val RadioButton       : TypedTag[Builder, Output, FragT]
      val RangeBoundTextBox : TypedTag[Builder, Output, FragT]
      val Select            : TypedTag[Builder, Output, FragT]
      val SimpleTextArea    : TypedTag[Builder, Output, FragT]
      val TextBox           : TypedTag[Builder, Output, FragT]
      val TextArea          : TypedTag[Builder, Output, FragT]
      val TimeTextBox       : TypedTag[Builder, Output, FragT]
      val ToggleButton      : TypedTag[Builder, Output, FragT]
      val ValidationTextBox : TypedTag[Builder, Output, FragT]
    }
  }
  trait dojo {
    trait store {
      val Memory : TypedTag[Builder, Output, FragT]
    }
  }
}
