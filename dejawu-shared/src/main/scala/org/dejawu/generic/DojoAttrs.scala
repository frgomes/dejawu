package org.dejawu.generic

// import acyclic.file
import scalatags.generic._


trait DojoAttrs[Builder, Output <: FragT, FragT] extends Util[Builder, Output, FragT]{
  val `data-dojo-id`    : Attr = "data-dojo-id".attr
  val `data-dojo-type`  : Attr = "data-dojo-type".attr
  val `data-dojo-props` : Attr = "data-dojo-props".attr
  val `data-dojo-value` : Attr = "data-dojo-value".attr
  val `data-dojo-event` : Attr = "data-dojo-event".attr
  val `data-dojo-args`  : Attr = "data-dojo-args".attr

  // alternative attributes
  val _djid    : Attr = "data-dojo-id".attr
  val _djtype  : Attr = "data-dojo-type".attr
  val _djprops : Attr = "data-dojo-props".attr
  val _djvalue : Attr = "data-dojo-value".attr
  val _djevent : Attr = "data-dojo-event".attr
  val _djargs  : Attr = "data-dojo-args".attr
}
