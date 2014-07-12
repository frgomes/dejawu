
package org.dejawu

import org.scalajs.dom

import scalatags.Text._
import scalatags.Text.all._
import scalatags.Escaping
import scalatags.Platform
import scalatags.generic
import scalatags.text

object DojoAttr {
  val `data-dojo-id`    : Attr = "data-dojo-id".attr
  val `data-dojo-type`  : Attr = "data-dojo-type".attr
  val `data-dojo-props` : Attr = "data-dojo-props".attr
}

object dijit {
    val calendar = "div".dtag[CalendarElement]("dijit.Calendar")
    class CalendarElement extends dom.HTMLDivElement
    val calendarlite = "div".dtag[CalendarLiteElement]("dijit.CalendarLite")
    class CalendarLiteElement extends dom.HTMLDivElement
    val colorpalette = "div".dtag[ColorPaletteElement]("dijit.ColorPalette")
    class ColorPaletteElement extends dom.HTMLDivElement
    val declaration = "div".dtag[DeclarationElement]("dijit.Declaration")
    class DeclarationElement extends dom.HTMLDivElement
    val dialog = "div".dtag[DialogElement]("dijit.Dialog")
    class DialogElement extends dom.HTMLDivElement
    val editor = "div".dtag[EditorElement]("dijit.Editor")
    class EditorElement extends dom.HTMLDivElement
    val fieldset = "div".dtag[FieldsetElement]("dijit.Fieldset")
    class FieldsetElement extends dom.HTMLDivElement
    val inlineeditbox = "div".dtag[InlineEditBoxElement]("dijit.InlineEditBox")
    class InlineEditBoxElement extends dom.HTMLDivElement
    val progressbar = "div".dtag[ProgressBarElement]("dijit.ProgressBar")
    class ProgressBarElement extends dom.HTMLDivElement
    val titlepane = "div".dtag[TitlePaneElement]("dijit.TitlePane")
    class TitlePaneElement extends dom.HTMLDivElement
    val toolbar = "div".dtag[ToolbarElement]("dijit.Toolbar")
    class ToolbarElement extends dom.HTMLDivElement
    val toolbarseparator = "span".dtag[ToolbarSeparatorElement]("dijit.ToolbarSeparator")
    class ToolbarSeparatorElement extends dom.HTMLSpanElement
    val tooltip = "div".dtag[TooltipElement]("dijit.Tooltip")
    class TooltipElement extends dom.HTMLDivElement
    val tooltipdialog = "div".dtag[TooltipDialogElement]("dijit.TooltipDialog")
    class TooltipDialogElement extends dom.HTMLDivElement
  object form {
      val button = "button".dtag[ButtonElement]("dijit.form.Button")
      class ButtonElement extends HTMLButtonElement
      val checkbox = "input".dtag[CheckBoxElement]("dijit.form.CheckBox")
      class CheckBoxElement extends dom.HTMLSelectElement
      val combobox = "input".dtag[ComboBoxElement]("dijit.form.ComboBox")
      class ComboBoxElement extends dom.HTMLSelectElement
      val combobutton = "div".dtag[ComboButtonElement]("dijit.form.ComboButton")
      class ComboButtonElement extends dom.HTMLDivElement
      val currencytextbox = "input".dtag[CurrencyTextBoxElement]("dijit.form.CurrencyTextBox")
      class CurrencyTextBoxElement extends dom.HTMLSelectElement
      val datetextbox = "input".dtag[DateTextBoxElement]("dijit.form.DateTextBox")
      class DateTextBoxElement extends dom.HTMLSelectElement
      val dropdownbutton = "div".dtag[DropDownButtonElement]("dijit.form.DropDownButton")
      class DropDownButtonElement extends dom.HTMLDivElement
      val filteringselect = "select".dtag[FilteringSelectElement]("dijit.form.FilteringSelect")
      class FilteringSelectElement extends dom.HTMLSelectElement
      val form = "div".dtag[FormElement]("dijit.form.Form")
      class FormElement extends dom.HTMLDivElement
      val mappedtextbox = "input".dtag[MappedTextBoxElement]("dijit.form.MappedTextBox")
      class MappedTextBoxElement extends dom.HTMLSelectElement
      val multiselect = "select".dtag[MultiSelectElement]("dijit.form.MultiSelect")
      class MultiSelectElement extends dom.HTMLSelectElement
      val numberspinner = "input".dtag[NumberSpinnerElement]("dijit.form.NumberSpinner")
      class NumberSpinnerElement extends dom.HTMLSelectElement
      val numbertextbox = "input".dtag[NumberTextBoxElement]("dijit.form.NumberTextBox")
      class NumberTextBoxElement extends dom.HTMLSelectElement
      val radiobutton = "input".dtag[RadioButtonElement]("dijit.form.RadioButton")
      class RadioButtonElement extends dom.HTMLSelectElement
      val rangeboundtextbox = "input".dtag[RangeBoundTextBoxElement]("dijit.form.RangeBoundTextBox")
      class RangeBoundTextBoxElement extends dom.HTMLSelectElement
      val select = "select".dtag[SelectElement]("dijit.form.Select")
      class SelectElement extends dom.HTMLSelectElement
      val simpletextarea = "textarea".dtag[SimpleTextareaElement]("dijit.form.SimpleTextarea")
      class SimpleTextareaElement extends HTMLTextAreaElement
      val textbox = "input".dtag[TextBoxElement]("dijit.form.TextBox")
      class TextBoxElement extends dom.HTMLSelectElement
      val textarea = "textarea".dtag[TextareaElement]("dijit.form.Textarea")
      class TextareaElement extends HTMLTextAreaElement
      val timetextbox = "input".dtag[TimeTextBoxElement]("dijit.form.TimeTextBox")
      class TimeTextBoxElement extends dom.HTMLSelectElement
      val togglebutton = "button".dtag[ToggleButtonElement]("dijit.form.ToggleButton")
      class ToggleButtonElement extends HTMLButtonElement
      val validationtextbox = "input".dtag[ValidationTextBoxElement]("dijit.form.ValidationTextBox")
      class ValidationTextBoxElement extends dom.HTMLSelectElement
  }
}
object dojo {
  object store {
      val memory = "div".dtag[MemoryElement]("dojo.store.Memory")
      class MemoryElement extends dom.HTMLDivElement
  }
}

implicit class DojoConversions(s: String) {
  def dtag[T <: Platform.Base](dtype: String) = {
    if (!Escaping.validTag(s))
      throw new IllegalArgumentException(s"Illegal tag name: $s is not a valid XML tag name")
    TypedTag(s, List(List(DojoAttr.`data-dojo-type` := dtype)), false)
    //makeAbstractTypedTag[T](s, List(List(`data-dojo-type` := dtype)), false)
  }
  //private def makeAbstractTypedTag[T <: Platform.Base](tag: String, modifiers: List[Seq[Modifier[Builder]]], void: Boolean): TypedTag[T] = {
  //  TypedTag(tag, modifiers, void)
  //}
}