package com.github.seanBE.supermarket.actors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.Set
import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class Till extends Actor with ActorLogging {

  var queue = Set[ActorRef]()

  override def preStart(): Unit = log.info("Opening {}.", self.path.name)
  override def receive = Actor.emptyBehavior
}

object Till {
  val props = Props[Till]
}
