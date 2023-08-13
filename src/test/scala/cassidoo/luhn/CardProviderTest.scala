package cassidoo.luhn

import cassidoo.UnitTestSpec
import org.scalatest.Inspectors

/**
  * IMPORTANT NOTE:
  * This test does not perform Luhn validity checks
  * on the input card numbers. These tests only verify
  * if the non-blank card numbers are associated with
  * known card providers.
  * 
  * For card number formula verification, see `LuhnTest`.
  * 
  * Tests and coverage could be improved :(
  */
class CardProviderTest extends UnitTestSpec {
  "CardProvider Logic" must {
    "detect the right card provider for a given card" in {
      val cardNumbers = Map(
        "5555555555554444" -> DinersClubCanadaAndUS,
        "4532015112830366" -> Visa,
        "6011514433546201" -> DiscoverCard,
        "6771549495586802" -> Laser
      )

      Inspectors.forEvery(cardNumbers) { (cardNo, expectedProvider) =>
        CardProvider.of(cardNo) mustEqual expectedProvider
      }
    }
  }
}
