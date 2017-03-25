package com.github.seanBE.supermarket.actors

import org.scalatest._
import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}

abstract class ActorSpec(_system: ActorSystem = ActorSystem())
  extends TestKit(_system) with ImplicitSender
  with WordSpecLike with BeforeAndAfterAll with Matchers {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

}
