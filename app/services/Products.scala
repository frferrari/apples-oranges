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

  /**
   * Computes the total price for a given list of products
   *
   * @param products  The list of products
   * @return          The computed price
   */
  def computeTotalPrice(products: List[String]): Future[BigDecimal] = {
    val prices = products.groupBy(p => p).map { case (productName, productList) =>
      getProductPrice(productName).map {
        case Some(price) => ProductOffers.getOfferByProduct(productName).calculateTotal(productList.size, price)
        case _ => BigDecimal(0)
      }
    }

    /* Traverse the list of future prices and sum them up */
    Future.fold(prices)(BigDecimal(0))(_ + _)
  }

  /**
   * Get the price of a product with a product name (use a future to simulate a non blocking db access)
   *
   * @param productName The product name
   * @return            The product price
   */
  def getProductPrice(productName: String): Future[Option[BigDecimal]] = Future {
    dbProductList.find(_.name == productName).map(_.price)
  }
}
