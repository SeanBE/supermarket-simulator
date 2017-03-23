package com.github.seanBE.supermarket.actors

import scala.collection.mutable.Set
import akka.actor.{Actor, ActorLogging, Props, ActorRef, ActorRefFactory}

class Supermarket(factory: ActorRefFactory => ActorRef, numTills: Int = 2)
    extends Actor with ActorLogging {

  import Supermarket.Product._

  val stock = Map[Product, Int](Eggs -> 15, Bacon -> 15, Booze -> 15)
  var tills = scala.collection.mutable.Queue.empty[ActorRef]

  override def receive = Actor.emptyBehavior

  override def preStart(): Unit = {
    log.info("Supermarket open for business.")
    for (i <- 0 until numTills) {
      //TODO how can we feed names to actor using factory approach?
      tills += factory(context)
    }
  }
}

abstract sealed class Product(val name: String)

object Supermarket {
  def props(factory: ActorRefFactory => ActorRef, numTills: Int) = Props(new Supermarket(factory, numTills))
  case object NewCustomer

  object Product  {
    case object Eggs extends Product("eggs")
    case object Bacon extends Product("bacon")
    case object Booze extends Product("booze")
    val values = List(Eggs, Bacon, Booze)
  }
}
