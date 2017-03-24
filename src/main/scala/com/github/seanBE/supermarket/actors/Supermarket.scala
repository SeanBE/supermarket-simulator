package com.github.seanBE.supermarket.actors

import scala.collection.mutable.Set
import akka.actor.{Actor, ActorLogging, Props, ActorRef, ActorRefFactory}
import utils._

class Supermarket(factory: NamedActorFactory, numTills: Int = 2)
    extends Actor with ActorLogging {

  import Supermarket.Product._

  val stock = Map[Product, Int](Eggs -> 15, Bacon -> 15, Booze -> 15)
  var tills = scala.collection.mutable.Queue.empty[ActorRef]
  var customers = Set[ActorRef]()
  var customerCount = 0

  override def receive = {
      case Supermarket.NewCustomer => {
        val customer = context.actorOf(Customer.props(5), "c" + customerCount)

        customerCount += 1
        customers += customer

        customer ! Customer.EnterShop
      }
  }

  override def preStart(): Unit = {
    log.info("Supermarket open for business.")
    for (i <- 0 until numTills) {
      tills += factory(context, "till" + i)
    }
  }

  override def postStop(): Unit = {
    super.postStop()
    log.info("{} customers visited the supermarket.", customerCount)
  }
}

abstract sealed class Product(val name: String)

object Supermarket {
  def props(factory: NamedActorFactory, numTills: Int) = Props(new Supermarket(factory, numTills))
  case object NewCustomer

  object Product  {
    case object Eggs extends Product("eggs")
    case object Bacon extends Product("bacon")
    case object Booze extends Product("booze")
    val values = List(Eggs, Bacon, Booze)
  }
}
