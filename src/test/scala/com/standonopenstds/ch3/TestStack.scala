package com.standonopenstds.ch3

import akka.actor.{Actor, ActorRef, Props, ActorSystem}
import akka.testkit.{TestActorRef, TestKit, TestLatch, ImplicitSender}
import scala.concurrent.duration._
import scala.concurrent.Await
import org.scalatest.{WordSpecLike, BeforeAndAfterAll}
import org.scalatest.Matchers

class TestStack extends TestKit(ActorSystem("StackSpec")) 
                with WordSpecLike
                with ImplicitSender
                with Matchers
                with BeforeAndAfterAll{
  
   override def afterAll {system.shutdown()}
   
   import Stack._
   
   "A stack Actor from guha actor book" must {         
     
     "Push and Pop should work a/c" in {
       val s = system.actorOf(Props[StackOp])
       
       s ! Push(2)
       
       expectMsg(OperationSuccess(2))
       
       s ! Push(3)
       
       expectMsg(OperationSuccess(3))
       
       s ! Pop
       
       expectMsg(OperationSuccess(3))
       
       s ! Pop
       
       expectMsg(OperationSuccess(2))
       
       s ! Pop
       
       expectMsg(OperationFailed("Cant pop from an empty stack"))
       
     }
     
   }  
   
   //TODO test wether we are creating new Actors on to the stack
  
}