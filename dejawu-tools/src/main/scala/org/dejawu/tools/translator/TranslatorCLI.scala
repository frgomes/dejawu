package org.dejawu.tools.translator

import org.rogach.scallop._


class TranslatorCLI(_args : Array[String]) extends ScallopConf(_args) {
  version("translator v0.1 (c) 2014, Richard Gomes")
  banner("""usage: translator [<input-file>] [<output-file>]
           |synopsis:
           |  Translate a HTML file to ScalaJS, employing Scalatags and Dejawu.
           |  More info on ScalaJS:      https://github.com/scala-js/scala-js
           |               Scalatags:    https://github.com/lihaoyi/scalatags
           |               Dejawu:       https://github.com/frgomes/dejawu
           |options:""".stripMargin)
  val input  = trailArg[String](descr="""Input  .html  file or "-" (without quotes) for stdin""")
  val output = trailArg[String](descr="""Output .scala file or "-" (without quotes) for stdout""")
}
