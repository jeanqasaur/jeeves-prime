package test.cap.jeeves.socialnet

import cap.scalasmt._
import cap.jeeves._
import cap.jeeves.socialnet._
import SocialNetBackend._

import org.scalatest.FunSuite
import org.scalatest.Assertions.{expect}

class TestUser extends FunSuite {
  
    val user1 = User(Username(""),Name(""),Password(""),Email(""),Birthday(1,1,1),Network(""))
    user1.setPassword(Password("Password"))
    
    test ("SelfPassword") {
    expect("Password") {
     user1.showPassword(SocialNetContext(user1))   
    // ""
   }
  }
}
