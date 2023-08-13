package cassidoo.luhn

import scala.annotation.tailrec

/**
  * https://buttondown.email/cassidoo/archive/it-isnt-the-mountains-ahead-to-climb-that-wear/
  */
object Luhn:
  final val CardNumIsBlankError  = "Card number cannot be blank"
  final val CardNumLenNotInRange = "Card number must be of length 12-19"

  def isCardNumberValid(fullCardNo: String): Either[String, Boolean] =
    generateChecksum(fullCardNo).map { cs =>
      val expected = fullCardNo.charAt(fullCardNo.length - 1) - '0'
      10 - (cs % 10) == expected
    }

  def generateChecksum(fullCardNo: String): Either[String, Int] =
    if fullCardNo.isBlank then Left(CardNumIsBlankError)
    else if !cardLengthInRange(fullCardNo) then Left(CardNumLenNotInRange)
    else
      Right(
        fullCardNo
          .dropRight(1)
          .reverseIterator
          .zipWithIndex
          .foldLeft(0) { case (total, (ch, idx)) =>
            val d   = ch - '0'
            val sum =
              if idx % 2 != 0 then d
              else sumDigitsOf(d * 2)

            total + sum
          }
      )

  def generateChecksumDigit(fullCardNo: String): Either[String, Int] =
    generateChecksum(fullCardNo).map(cs => 10 - (cs % 10))

  private[luhn] def cardLengthInRange(no: String): Boolean =
    val len = no.length
    len >= 12 && len <= 19

  private[luhn] def sumDigitsOf(n: Int): Int = {
    @tailrec
    def loop(n: Int, res: Int): Int =
      if n == 0 then res
      else loop(n / 10, res + (n % 10))

    loop(n, 0)
  }
