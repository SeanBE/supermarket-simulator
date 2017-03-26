package com.github.seanBE.supermarket.actors

import scala.collection.mutable.Set
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{Actor, ActorLogging, ActorRef, Props, Status}

class Till(cashier: ActorRef) extends Actor with ActorLogging {

  var queue = Set[ActorRef]()

  override def preStart(): Unit = log.info("Opening {}.", self.path.name)

  override def receive = {
    case Till.JoinTill =>
      log.info("{} joining {} ({} in line).", sender.path.name, self.path.name, queue.size)
      queue += sender
      sender ! cashier
    case Till.LeaveTill =>
      log.info("{} leaving {}.", sender.path.name, self.path.name)
      queue -= sender

    case _ => log.info("Unknown request.")
  }
}

object Till {
  def props(cashier: ActorRef) = Props(new Till(cashier))
  case object JoinTill
  case object LeaveTill
}
