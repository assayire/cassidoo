package cassidoo.luhn

// https://www.groundlabs.com/blog/anatomy-of-a-credit-card/
sealed trait CardProvider { self =>
  override val toString: String = self.getClass.getSimpleName.replace("$", "")
}

object CardProvider:
  def of(cardNo: String): CardProvider =
    cardNo match
      case AmericanExpress()         => AmericanExpress
      case DinersClubCanadaAndUS()   => DinersClubCanadaAndUS
      case DinersClubCarteBlanche()  => DinersClubCarteBlanche
      case DinersClubInternational() => DinersClubInternational
      case DiscoverCard()            => DiscoverCard
      case InstaPayment()            => InstaPayment
      case JCB()                     => JCB
      case Laser()                   => Laser
      case Maestro()                 => Maestro
      case MasterCard()              => MasterCard
      case Visa()                    => Visa
      case VisaElectron()            => VisaElectron
      case _                         => UnknownProvider

case object AmericanExpress extends CardProvider:
  def unapply(no: String): Boolean =
    no.length == 15 && (no.startsWith("34") || no.startsWith("37"))

case object DinersClubCarteBlanche extends CardProvider:
  def unapply(no: String): Boolean =
    no.length == 14 && {
      val prefix = no.subSequence(0, 3).toString.toInt
      prefix >= 300 && prefix <= 305
    }

case object DinersClubInternational extends CardProvider:
  def unapply(no: String): Boolean =
    no.length == 14 && no.startsWith("36")

case object DinersClubCanadaAndUS extends CardProvider:
  def unapply(no: String): Boolean =
    no.length == 16 && (no.startsWith("54") || no.startsWith("55"))

case object DiscoverCard extends CardProvider:
  def isIn622xxRange(no: String): Boolean =
    val prefix = no.subSequence(0, 6).toString.toInt
    prefix >= 622126 && prefix <= 622925

  def isIn64xRange(no: String): Boolean =
    val prefix = no.subSequence(0, 3).toString.toInt
    prefix >= 644 && prefix <= 649

  def unapply(no: String): Boolean =
    no.length == 16 && {
      no.startsWith("6011") ||
      no.startsWith("65") ||
      isIn622xxRange(no) ||
      isIn64xRange(no)
    }

case object InstaPayment extends CardProvider:
  def unapply(no: String): Boolean =
    no.length == 16 && {
      val prefix = no.subSequence(0, 3).toString.toInt
      prefix >= 637 && prefix <= 639
    }

case object JCB extends CardProvider:
  def unapply(no: String): Boolean =
    no.length == 16 && {
      val prefix = no.subSequence(0, 4).toString.toInt
      prefix >= 3528 && prefix <= 3589
    }

case object Laser extends CardProvider:
  def unapply(no: String): Boolean =
    lenOk(no) && prefixOk(no)

  def lenOk(no: String): Boolean =
    val len = no.length
    len >= 16 && len <= 19

  def prefixOk(no: String): Boolean =
    no.startsWith("6304") ||
      no.startsWith("6706") ||
      no.startsWith("6771") ||
      no.startsWith("6709")

case object Maestro extends CardProvider:
  private val prefixes = List("5018", "5020", "5038", "6304", "6759", "6761", "6762", "6763")

  def unapply(no: String): Boolean =
    lenOk(no) && prefixes.exists(no.startsWith)

  def lenOk(no: String): Boolean =
    val len = no.length
    len >= 12 && len <= 19

case object MasterCard extends CardProvider:
  def unapply(no: String): Boolean =
    no.length == 16 && prefixOk(no)

  def prefixOk(no: String): Boolean =
    val prefix = no.subSequence(0, 2).toString.toInt
    prefix >= 51 && prefix <= 55

case object Visa extends CardProvider:
  def unapply(no: String): Boolean =
    lenOk(no) && no.startsWith("4")

  def lenOk(no: String): Boolean =
    val len = no.length
    len >= 13 && len <= 16

case object VisaElectron extends CardProvider:
  private val prefixes = List("4026", "417500", "4508", "4844", "4913", "4917")

  def unapply(no: String): Boolean =
    no.length == 16 && prefixes.exists(no.startsWith)

case object UnknownProvider extends CardProvider
