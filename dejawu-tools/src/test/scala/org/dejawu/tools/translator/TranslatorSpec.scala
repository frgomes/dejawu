package org.dejawu.tools.translator

import java.io.InputStream
import org.scalatest._


class TranslatorSpec extends FeatureSpec with GivenWhenThen {

  val tool = new Translator

  feature("Ability parse command line arguments") {
    scenario("Parsing a command line with custom configuration file") {
      val cmd = new CLI(Array("Example.html", "Example.scala")).parse
      assert( cmd.isDefined )
      assert( cmd.get.input  === Some("Example.html") )
      assert( cmd.get.output === Some("Example.scala") )
    }
    scenario("Parsing a command line specifying standard input") {
      val cmd = new CLI(Array("-", "Example.scala")).parse
      assert( cmd.isDefined )
      assert( cmd.get.input  === Some("-") )
      assert( cmd.get.output === Some("Example.scala") )
    }
    scenario("Parsing a command line specifying standard output") {
      val cmd = new CLI(Array("Example.html", "-")).parse
      assert( cmd.isDefined )
      assert( cmd.get.input  === Some("Example.html") )
      assert( cmd.get.output === Some("-") )
    }
    scenario("Parsing a command line with both standard input and standard output") {
      val cmd = new CLI(Array("-", "-")).parse
      assert( cmd.isDefined )
      assert( cmd.get.input  === Some("-") )
      assert( cmd.get.output === Some("-") )
    }
    scenario("Parsing a command line with standard output missing") {
      val cmd = new CLI(Array("Example.html")).parse
      assert( cmd.isDefined )
      assert( cmd.get.input  === Some("Example.html") )
      assert( cmd.get.output === None )
    }
    scenario("Parsing a command line with both standard input andstandard output missing") {
      val cmd = new CLI(Array("Example.html")).parse
      assert( cmd.isDefined )
      assert( cmd.get.input  === None )
      assert( cmd.get.output === None )
    }

    //TODO: these test cases
    // The following test cases call System.exit and interrupt execution of the test suite :(

    // scenario("Requesting help") {
    //   val cmd = new CLI(Array("--help"))
    // }
    // scenario("Requesting version information") {
    //   val cmd = new CLI(Array("--version"))
    // }
  }

  import java.io.{FileInputStream,FileOutputStream}

  feature("Ability parse command line arguments and generate output") {
    scenario("Parsing a command line with custom configuration file") {
      val cmd = new CLI(
        Array(
          "~/sources/github.com/dojo/demos/themePreviewer/demo.html",
          "/tmp/demo.scala"))
        .parse
      val tool = new Translator
      assert( cmd.isDefined )
      tool.translate( Some(cmd.get.input), Some(cmd.get.output) )
    }

    //TODO: these test cases
    //should be necessary to intercept stdin in order to do this

    // scenario("Parsing a command line with input from stdin") {
    //   val cmd = new CLI(Array("-c", "-", "/tmp/DojoTags3.scala")).parse
    //   val tool = new Translator
    //   assert(cmd.isDefined)
    //   tool.generate( Some(cmd.get.input), Some(cmd.get.output) )
    // }
  }

}
