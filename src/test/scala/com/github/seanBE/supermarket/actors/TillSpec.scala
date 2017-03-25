package com.github.seanBE.supermarket.actors

import akka.actor.{Props, ActorSystem, ActorRef}
import akka.util.Timeout
import org.scalatest._
import akka.testkit.{ImplicitSender, TestKit, TestActorRef, TestProbe}
import scala.concurrent.duration._
import scala.concurrent.Await
import akka.pattern.ask
import scala.util.Success

class TillSpec extends TestKit(ActorSystem("TillSpec"))
  with ImplicitSender
  with WordSpecLike
  with BeforeAndAfterAll
  with Matchers {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A Till " must {
        "accept JoinTill requests" in {
          implicit val timeout = Timeout(5 seconds)
          val cashierProbe = TestProbe()
          val tillRef = TestActorRef(Till.props(cashierProbe.ref))
          val future = tillRef ? Till.JoinTill
          val Success(result: ActorRef) = future.value.get
          result should be(cashierProbe.ref)
        }
      }
}
