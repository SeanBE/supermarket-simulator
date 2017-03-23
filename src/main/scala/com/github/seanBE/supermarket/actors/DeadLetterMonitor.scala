package com.github.seanBE.supermarket.actors

import akka.actor.{DeadLetter, Actor, ActorLogging, Props}

class DeadLetterMonitor extends Actor with ActorLogging {
  def receive = {
    case d: DeadLetter => log.error(s"DeadLetterMonitorActor : saw dead letter $d")
    case _ => log.info("DeadLetterMonitorActor : got a message")
  }
}

object DeadLetterMonitor {
  val props = Props[DeadLetterMonitor]
}
