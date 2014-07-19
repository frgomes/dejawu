package org.dejawu


object DojoText extends scalatags.generic.Aliases[scalatags.text.Builder, String, String] {

  object all extends scalatags.Text.Cap with org.dejawu.text.DojoTags


  // FIXME: (THIS IS DIRTY) :: should not copy/paste from scalatags.Text
  object StringFrag extends scalatags.Companion[StringFrag]
  case class StringFrag(v: String) extends scalatags.text.Frag{
    def render = {
      val strb = new StringBuilder()
      writeTo(strb)
      strb.toString()
    }
    def writeTo(strb: StringBuilder) = scalatags.Escaping.escape(v, strb)
  }

  // FIXME: (THIS IS DIRTY) :: should not copy/paste from scalatags.Text
  object RawFrag    extends scalatags.Companion[RawFrag]
  case class RawFrag(v: String) extends scalatags.text.Frag {
    def render = v
    def writeTo(strb: StringBuilder) = strb ++= v
  }
}

/*
 object DojoText extends generic.Bundle[scalatags.text.Builder, String, String] {
 with scalatags.generic.Aliases[scalatags.text.Builder, String, String] {

 object attrs     extends scalatags.Text.Cap with scalatags.Text.Attrs
 object tags      extends scalatags.Text.Cap with scalatags.text.Tags
 object tags2     extends scalatags.Text.Cap with scalatags.text.Tags2
 object styles    extends scalatags.Text.Cap with scalatags.Text.Styles
 object styles2   extends scalatags.Text.Cap with scalatags.generic.Styles2[scalatags.text.Builder, String, String]
 object svgTags   extends scalatags.Text.Cap with scalatags.text.SvgTags
 object svgStyles extends scalatags.Text.Cap with scalatags.Text.SvgStyles


 object implicits extends scalatags.Text.Aggregate

 object al
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
 */
