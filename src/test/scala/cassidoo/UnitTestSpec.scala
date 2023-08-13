package cassidoo

import org.scalatest.{EitherValues, OptionValues}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

trait UnitTestSpec extends AnyWordSpec with Matchers with EitherValues with OptionValues
