package com.github.seanBE.supermarket.actors

import org.scalatest._
import scala.collection.mutable.ListBuffer
import utils._
import akka.actor.{ActorSystem, ActorRefFactory}
import akka.testkit.{ImplicitSender, TestKit, TestActorRef, TestProbe}

class SupermarketSpec extends TestKit(ActorSystem("SupermarketSpec"))
  with ImplicitSender with WordSpecLike with BeforeAndAfterAll with Matchers {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "a Supermarket" must {
      "generate a number of tills" in {
        val expectedTillCount = 4
        val probes = ListBuffer.empty[(TestProbe)]

        val faker:NamedActorFactory = (ctx, name) => {
            val probe = TestProbe()
            probes += probe
            probe.ref
          }

        val supermarket = TestActorRef(Supermarket.props(faker, expectedTillCount))
        assert(probes.size == expectedTillCount)
      }
  }

}
