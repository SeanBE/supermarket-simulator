package com.github.seanBE.supermarket.actors

import akka.actor.{Actor, ActorLogging, Props}

class Cashier extends Actor with ActorLogging {
  override def receive = {
    case "Hello" => sender ! "Hello, I am the cashier!"
    case "Checkout" => sender ! Cashier.Receipt(Nil)
    case _ =>
      log.info("Unknown Request")
      sender ! "Huh?"
  }
}

object Cashier {
  val props = Props[Cashier]
  case class Receipt(val products: Seq[String])
}
