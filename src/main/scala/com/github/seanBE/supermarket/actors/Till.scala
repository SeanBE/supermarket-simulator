package com.github.seanBE.supermarket.actors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.Set
import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class Till(cashier: ActorRef) extends Actor with ActorLogging {

  var queue = Set[ActorRef]()

  override def preStart(): Unit = log.info("Opening {}.", self.path.name)

  override def receive = {
    case Till.JoinTill =>
      log.info("{} joining {} ({} in line).", sender.path.name, self.path.name, queue.size)
      queue += sender
      sender ! cashier

    case _ => log.info("Unknown request.")
  }
}

object Till {
  def props(cashier: ActorRef) = Props(new Till(cashier))
  case object JoinTill
}
