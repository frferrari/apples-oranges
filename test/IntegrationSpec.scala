import akka.stream.Materializer
import controllers.ShopController
import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

import scala.math.BigDecimal

class IntegrationSpec extends PlaySpec with OneServerPerSuite with HtmlUnitFactory {

  val shopController = new ShopController

  "ShopController" should {

    implicit lazy val materializer: Materializer = app.materializer

    "return 0 for an empty list of products" in {
      val request = FakeRequest("GET", "/checkout").withJsonBody(Json.parse(s"""[ ]""".stripMargin))
      val postResult = call(shopController.checkout, request)
      val jsonResult = contentAsJson(postResult)

      status(postResult) mustEqual OK
      (jsonResult \ "total").as[BigDecimal] mustEqual(BigDecimal(0))
    }

    "return 0 for a list of invalid product names" in {
      val request = FakeRequest("GET", "/checkout").withJsonBody(Json.parse(
        s"""[ "Kiwi", "Grapes" ]""".stripMargin))
      val postResult = call(shopController.checkout, request)
      val jsonResult = contentAsJson(postResult)

      status(postResult) mustEqual OK
      (jsonResult \ "total").as[BigDecimal] mustEqual(BigDecimal(0.0))
    }

    "compute the total price for 1 Apple and 1 Orange" in {
      val request = FakeRequest("GET", "/checkout").withJsonBody(Json.parse(
        s"""[ "Apple", "Orange" ]""".stripMargin))
      val postResult = call(shopController.checkout, request)
      val jsonResult = contentAsJson(postResult)

      status(postResult) mustEqual OK
      (jsonResult \ "total").as[BigDecimal] mustEqual(BigDecimal(0.85))
    }

    "compute the total price for 2 Apples and 1 Orange and 1 unknown product" in {
      val request = FakeRequest("GET", "/checkout").withJsonBody(Json.parse(
        s"""[ "Orange", "Apple", "Kiwi", "Apple" ]""".stripMargin))
      val postResult = call(shopController.checkout, request)
      val jsonResult = contentAsJson(postResult)

      status(postResult) mustEqual OK
      (jsonResult \ "total").as[BigDecimal] mustEqual(BigDecimal(1.45))
    }
  }

}
