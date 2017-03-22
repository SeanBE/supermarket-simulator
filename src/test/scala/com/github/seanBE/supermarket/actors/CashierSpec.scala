package com.github.seanBE.supermarket.actors

import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import org.scalatest._
import akka.testkit.{ImplicitSender, TestKit, TestActorRef}
import scala.concurrent.duration._
import scala.concurrent.Await
import akka.pattern.ask
import scala.util.Success

class CashierSpec extends TestKit(ActorSystem("CashierSpec"))
  with ImplicitSender
  with WordSpecLike
  with BeforeAndAfterAll
  with Matchers {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A Cashier using implicit sender " must {
      "say hello" in {
        val cashier = system.actorOf(Props[Cashier])
        cashier ! "Hello"
        expectMsg("Hello, I am the cashier!")
      }
      "send confused response if request unknown" in {
        val cashier = system.actorOf(Props[Cashier])
        cashier ! "Give me all your money."
        expectMsg("Huh?")
      }
  }

  "An Cashier using TestActorRef " must {
        "send a receipt when checking out" in {
          implicit val timeout = Timeout(5 seconds)
          val cashierRef = TestActorRef(new Cashier)
          val future = cashierRef ? "Checkout"
          val Success(result: Cashier.Receipt) = future.value.get
          result should be(Cashier.Receipt(Nil))
        }
      }
}
