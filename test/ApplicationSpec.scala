import org.scalatestplus.play._
import play.api.test.Helpers._
import services.Products

import scala.math.BigDecimal

class ApplicationSpec extends PlaySpec with OneServerPerSuite with HtmlUnitFactory {

  "Products object" should {
    "return 0 for an empty list of products" in {
      await(Products.computeTotalPrice(Nil)) mustEqual(BigDecimal(0))
    }

    "return 0 for a list of unknown products" in {
      await(Products.computeTotalPrice(List("Kiwi"))) mustEqual(BigDecimal(0))
    }

    "return 0.60 for one Apple" in {
      await(Products.computeTotalPrice(List("Apple"))) mustEqual(BigDecimal(0.60))
    }

    "return 1.20 for two Apples" in {
      await(Products.computeTotalPrice(List("Apple", "Apple"))) mustEqual(BigDecimal(1.20))
    }

    "return 0.25 for one Orange" in {
      await(Products.computeTotalPrice(List("Orange"))) mustEqual(BigDecimal(0.25))
    }

    "return 0.50 for two Oranges" in {
      await(Products.computeTotalPrice(List("Orange", "Orange"))) mustEqual(BigDecimal(0.50))
    }

    "return 0.85 for one Apple and one Orange" in {
      await(Products.computeTotalPrice(List("Apple", "Orange"))) mustEqual(BigDecimal(0.85))
    }

    "return 1.10 for one Apple and two Oranges" in {
      await(Products.computeTotalPrice(List("Orange", "Apple", "Orange"))) mustEqual(BigDecimal(1.10))
    }

    "return 1.70 for two Apples and two Oranges" in {
      await(Products.computeTotalPrice(List("Orange", "Apple", "Orange", "Apple"))) mustEqual(BigDecimal(1.70))
    }

    "return 1.70 for two Apples and two Oranges and some unknown products" in {
      await(Products.computeTotalPrice(List("Kiwi", "Orange", "Apple", "Grapes", "Orange", "Apple", "Kiwi"))) mustEqual(BigDecimal(1.70))
    }
  }

}
