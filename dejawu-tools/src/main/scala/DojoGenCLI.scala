package org.dejawu.tools

import org.rogach.scallop._


class DojoGenCLI(_args : Array[String]) extends ScallopConf(_args) {
  version("dojogen v0.1 (c) 2014, Richard Gomes")
  banner("""usage: dojogen [options] <trailing-arguments>
           |synopsis:
           |  Generates Dojo Toolkit wrappers for Dejawu.
           |  Dejawu employs ScalaJS in order to deliver rich single page webapps written in Scala.
           |  Dejawu tags (which wrap Dojo Toolkit tags) can be intermixed with tags provided by Scalatags.
           |  More info on Dojo Toolkit: http://demos.dojotoolkit.org
           |               ScalaJS:      https://github.com/scala-js/scala-js
           |               Dejawu:       https://github.com/frgomes/dejawu
           |               Scalatags:    https://github.com/lihaoyi/scalatags
           |options:""".stripMargin)
  val config = opt[String]("config", short='c', default=None, descr="""Configuration which defines how Dojo wrappers should be generated. """ +
                                                                    """If "-" (without quotes) is passed, the configuration is read from stdin.""")
  val output = trailArg[String](descr="""Output .scala file to be generated""")
}