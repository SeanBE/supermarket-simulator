package com.github.seanBE.supermarket

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import akka.actor.{ActorSystem, ActorRefFactory}
import scala.concurrent.ExecutionContext.Implicits.global

import actors.{Supermarket, Till}

object Application extends App {

    // Init system and create manager actor.
    val system = ActorSystem("SupermarketSystem")

    val tillMaker = (f: ActorRefFactory) => f.actorOf(Till.props)
    val supermarket = system.actorOf(Supermarket.props(tillMaker, 2), "superMarket")

    try {
      // Run simulation for 30 seconds.
      system.awaitTermination(Duration.create(30, TimeUnit.SECONDS))
    } catch {
      case e: Throwable => system.shutdown()
    }
}
