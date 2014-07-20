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
