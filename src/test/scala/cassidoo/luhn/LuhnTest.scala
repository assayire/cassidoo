package cassidoo.luhn

import cassidoo.luhn.Luhn.*
import cassidoo.{NoShrinkMixin, UnitTestSpec}
import org.scalacheck.Gen
import org.scalatest.Inside.inside
import org.scalatest.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks.forAll as forAllInGen

// Tests and coverage could be improved :(
class LuhnTest extends UnitTestSpec with NoShrinkMixin {
  "Luhn" must {
    "report error for zero-length input" in {
      isCardNumberValid("") must matchPattern { case Left(Luhn.CardNumIsBlankError) =>
      }
    }

    "report error for blank input" in {
      isCardNumberValid("   ") must matchPattern { case Left(Luhn.CardNumIsBlankError) =>
      }
    }

    "report error if card number length not in range" in {
      val invalidLenCardsGen: Gen[String] =
        for {
          l  <- Gen.oneOf(Gen.chooseNum(1, 11), Gen.chooseNum(20, 100))
          cs <- Gen.listOfN(l, Gen.numChar)
        } yield cs.mkString

      forAllInGen(invalidLenCardsGen) { cardNo =>
        isCardNumberValid(cardNo) must matchPattern { case Left(CardNumLenNotInRange) =>
        }
      }

      forAllInGen(invalidLenCardsGen) { cardNo =>
        generateChecksumDigit(cardNo) must matchPattern { case Left(CardNumLenNotInRange) =>
        }
      }
    }
  }

  "report invalid card numbers" in {
    isCardNumberValid("5555555555554440") must matchPattern { case Right(false) =>
    }
  }

  "confirm valid card numbers" in {
    val cardNumbers = List(
      "5555555555554444",
      "4532015112830366",
      "6011514433546201",
      "6771549495586802"
    )

    Inspectors.forEvery(cardNumbers) { cardNo =>
      isCardNumberValid(cardNo).value mustBe true

      inside(generateChecksumDigit(cardNo)) { case Right(actual) =>
        actual mustEqual cardNo.last - '0'
      }
    }
  }
}
