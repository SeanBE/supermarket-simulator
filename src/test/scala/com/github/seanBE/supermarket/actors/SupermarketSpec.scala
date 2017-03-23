package com.github.seanBE.supermarket.actors

import akka.actor.{ActorSystem, ActorRefFactory}
import org.scalatest._
import akka.testkit.{ImplicitSender, TestKit, TestActorRef, TestProbe}
import scala.collection.mutable.ListBuffer

class SupermarketSpec extends TestKit(ActorSystem("SupermarketSpec"))
  with ImplicitSender with WordSpecLike with BeforeAndAfterAll with Matchers {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "a Supermarket" must {
      "generate a number of tills" in {
        val expectedTillCount = 4
        val probes = ListBuffer.empty[(TestProbe)]

        // test factory.
        val maker = (_: ActorRefFactory) => {
            val probe = TestProbe()
            probes += probe
            probe.ref
          }

        val supermarket = TestActorRef(Supermarket.props(maker, expectedTillCount))
        assert(probes.size == expectedTillCount)
      }
  }

}
