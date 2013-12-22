package com.standonopenstds.ch3

import akka.actor._
import akka.actor.Terminated

object Stack {
  
  case class Push(content: Int)
  case class Pop
  case class PushOp(requester:ActorRef, content: Int)
  case class PopOp(requester: ActorRef)  
  case class OperationOk(requester:ActorRef,content: Int, next:ActorRef)  
  case class OperationFailed(message: String)
  case class OperationSuccess(content: Int)
  
}

class Stack(val content: Int, next: ActorRef) extends Actor {
  
  import Stack._
  def receive = {
    
    case PopOp(r) => sender ! OperationOk(r,content,next)
                     println("killing .. " + self.path)
                     context.stop(self)
    
    case PushOp(r,v) => val a = context.actorOf(Props(new Stack(v, self)),""+v)
                        println(a.path)
                        sender ! OperationOk(r,v,a)
 
  }
  
}

class StackOp extends Actor {
  import Stack._
  var topOfStack = Actor.noSender
  
  
  def receive = {
    
    case Pop => if (topOfStack == Actor.noSender)
                  sender ! OperationFailed("Cant pop from an empty stack")
                else 
                  topOfStack ! PopOp(sender)
                  
    case Push(v) => if(topOfStack == Actor.noSender){
                      topOfStack = context.actorOf(Props(new Stack(v,Actor.noSender)), ""+v)
                      sender ! OperationSuccess(v)
                     }
                     else topOfStack ! PushOp(sender, v)
    case OperationOk(r,v,n) => topOfStack = n
                               r ! OperationSuccess(v)    
    
    
  }
  
}

