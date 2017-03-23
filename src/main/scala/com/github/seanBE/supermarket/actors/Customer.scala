package com.github.seanBE.supermarket.actors

import akka.actor.{Actor, ActorLogging, Props, ActorRef}

class Customer(itemsNeeded: Int = 10) extends Actor with ActorLogging {

  override def receive = {
    case Customer.EnterShop => log.info("{} has entered the shop.",self.path.name)
    case _ => log.info("I am just shopping!")
  }
}

object Customer {
  def props(itemsNeeded: Int) = Props(new Customer(itemsNeeded))
  case object EnterShop
}
