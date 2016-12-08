package services

import models.Product

import scala.concurrent.Future
import scala.math.BigDecimal
import scala.concurrent.ExecutionContext.Implicits.global

/*
 * To speed-up the development we use a list of products that is hard-coded,
 * In the real life we would probably access a database of products
 */
object Products {
  val dbProductList = List(Product("Apple", 0.60), Product("Orange", 0.25))

  def computeTotalPrice(products: List[String]): Future[BigDecimal] = Future {
    products.foldLeft(BigDecimal(0))(addProductPrice)
  }

  def addProductPrice(acc: BigDecimal, productName: String): BigDecimal = {
    val productPrice = dbProductList.find(_.name == productName).map(_.price).getOrElse(BigDecimal(0))

    acc + productPrice
  }
}
