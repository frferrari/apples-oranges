package controllers

import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.ExecutionContext.Implicits.global

import services.Products

/**
 *
 */
class ShopController extends Controller {

  def checkout = Action.async(BodyParsers.parse.json) { request =>
    val productList = request.body.as[List[String]]

    Products.computeTotalPrice(productList).map(p => Ok(Json.obj("status" -> "OK", "total" -> p)))
  }
}
