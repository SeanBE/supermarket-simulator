package com.github.seanBE.supermarket.actors

import akka.actor.{ActorRef, ActorRefFactory}

package object utils {
  type ActorFactory = ActorRefFactory => ActorRef
  type NamedActorFactory = (ActorRefFactory, String) => ActorRef
}
