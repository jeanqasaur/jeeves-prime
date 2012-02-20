package test.cap.jeeves.socialnet

import cap.scalasmt._
import cap.jeeves._
import cap.jeeves.socialnet._
import SocialNetBackend._

import org.scalatest.FunSuite
import org.scalatest.Assertions.{expect}

class TestUser extends FunSuite {
  
    val sn = SocialNetBackend

    val emptyList: List[String] = List[String]()
    
    val user1 = User(Username("U1"),Name("Name1"),Password("P1"),Email("1@test.com"),Birthday(1,1,1),
        Network("N1"),emptyList)
    val user2 = User(Username("U2"),Name("Name2"),Password("P2"),Email("2@test.com"),Birthday(2,2,2),
        Network("N1"),emptyList)
    val user3 = User(Username("U3"),Name("Name3"),Password("P3"),Email("3@test.com"),Birthday(3,3,3),
        Network("N2"),emptyList)
    
    sn.addUser("U1", user1)
    sn.addUser("U2", user2)
    sn.addUser("U3", user3)
    
    val testList = sn.searchByNetwork("N1", user1) 

    user1.setPassword(Password("Password"))

    
    test ("SelfPassword") {
    expect("Password") {
     user1.showPassword(SocialNetContext(user1))   
     }
   }
    
    //Tests that user2 should not be able to see user1's password.
    test ("OtherPassword") {
      expect("--") {
      user1.showPassword(SocialNetContext(user2))
      }
    }
    
   test ("SelfNetwork") {
     expect("N1") {
       user1.showNetwork(SocialNetContext(user1))
     }
   }
         
   test ("SameNetwork") {
     expect("N1") {
       user1.showNetwork(SocialNetContext(user2))
     }
   }
   
   test ("OtherNetwork") {
     expect("--") {
       user1.showNetwork(SocialNetContext(user3))
     }
   }
   
  // val testList = sn.searchByNetwork("N1", user1) 
   test ("NumberOfElements") {
     expect(2) {
       testList.length
     }
   }
}
