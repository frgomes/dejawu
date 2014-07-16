package org.dejawu.generic

// import acyclic.file
import scalatags.generic._


trait DojoAttrs[Builder, Output <: FragT, FragT] extends Util[Builder, Output, FragT]{
  val djid    : Attr = "data-dojo-id".attr
  val djtype  : Attr = "data-dojo-type".attr
  val djprops : Attr = "data-dojo-props".attr
}
