import org.scalatest.featureSpec

class DojoGeneratorSpec extends FeatureSpec {

  feature("Ability to read a Properties file") {
    scenario("Lines may contain dojoClass=tag,htmlElement") {
      val gen = new DojoGenerator()
    }
  }
