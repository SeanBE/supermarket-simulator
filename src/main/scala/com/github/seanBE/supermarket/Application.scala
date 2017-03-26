package com.github.seanBE.supermarket

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import akka.actor.{ActorSystem, DeadLetter}
import scala.concurrent.ExecutionContext.Implicits.global

import actors.utils._
import actors.{Supermarket, Till, DeadLetterMonitor, Cashier}

object Application extends App {

    // Init system and create manager actor.
    val system = ActorSystem("SupermarketSystem")

    val tillMaker:NamedActorFactory = (ctx, name) => {
      // TODO this is not scalable...
      val cashier = ctx.actorOf(Cashier.props)
      ctx.actorOf(Till.props(cashier), name)
    }

    val supermarket = system.actorOf(Supermarket.props(tillMaker, 2), "superMarket")

    val deadLetterMonitor = system.actorOf(DeadLetterMonitor.props, "dLetterMonitor")
    system.eventStream.subscribe(deadLetterMonitor, classOf[DeadLetter])

    // TODO use routing.
    // Create new Customer Actor every 2 seconds.
    system.scheduler.schedule(
        Duration.Zero,
        Duration.create(2, TimeUnit.SECONDS),
        supermarket,
        Supermarket.NewCustomer)

    try {
      // Run simulation for 30 seconds.
      // TODO deprecated..use Future with gracefulStop.
      system.awaitTermination(Duration.create(30, TimeUnit.SECONDS))
    } catch {
      case e: Throwable => system.terminate()
    }
}
