package cassidoo

import org.scalacheck.Shrink

trait NoShrinkMixin {
  implicit def noShrink[A]: Shrink[A] = Shrink.shrinkAny[A]
}
