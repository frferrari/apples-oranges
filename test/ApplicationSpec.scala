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

    "return 0.60 for two Apples (special offer)" in {
      await(Products.computeTotalPrice(List("Apple", "Apple"))) mustEqual(BigDecimal(0.60))
    }

    "return 1.20 for four Apples (special offer)" in {
      await(Products.computeTotalPrice(List("Apple", "Apple", "Apple", "Apple"))) mustEqual(BigDecimal(1.20))
    }

    "return 1.80 for five Apples (special offer)" in {
      await(Products.computeTotalPrice(List("Apple", "Apple", "Apple", "Apple", "Apple"))) mustEqual(BigDecimal(1.80))
    }

    "return 0.25 for one Orange" in {
      await(Products.computeTotalPrice(List("Orange"))) mustEqual(BigDecimal(0.25))
    }

    "return 0.50 for two Oranges" in {
      await(Products.computeTotalPrice(List("Orange", "Orange"))) mustEqual(BigDecimal(0.50))
    }

    "return 0.50 for three Oranges (special offer)" in {
      await(Products.computeTotalPrice(List("Orange", "Orange", "Orange"))) mustEqual(BigDecimal(0.50))
    }

    "return 1.00 for six Oranges and some unknown products (special offer)" in {
      await(Products.computeTotalPrice(List("Orange", "Orange", "Kiwi", "Orange", "Grape", "Orange", "Orange", "Kiwi"))) mustEqual(BigDecimal(1.00))
    }

    "return 0.85 for one Apple and one Orange" in {
      await(Products.computeTotalPrice(List("Apple", "Orange"))) mustEqual(BigDecimal(0.85))
    }

    "return 1.10 for one Apple and two Oranges" in {
      await(Products.computeTotalPrice(List("Orange", "Apple", "Orange"))) mustEqual(BigDecimal(1.10))
    }

    "return 1.10 for two Apples and two Oranges (special offer)" in {
      await(Products.computeTotalPrice(List("Orange", "Apple", "Orange", "Apple"))) mustEqual(BigDecimal(1.10))
    }

    "return 1.10 for two Apples and two Oranges and some unknown products" in {
      await(Products.computeTotalPrice(List("Kiwi", "Orange", "Apple", "Grapes", "Orange", "Apple", "Kiwi"))) mustEqual(BigDecimal(1.10))
    }
  }

}
