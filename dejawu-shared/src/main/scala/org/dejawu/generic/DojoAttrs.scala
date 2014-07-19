package org.dejawu.generic

// import acyclic.file
import scalatags.generic._


trait DojoAttrs[Builder, Output <: FragT, FragT] extends Util[Builder, Output, FragT]{
  val `data-dojo-id`    : Attr = "data-dojo-id".attr
  val `data-dojo-type`  : Attr = "data-dojo-type".attr
  val `data-dojo-props` : Attr = "data-dojo-props".attr
}
