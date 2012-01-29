package test.cap.jeeves.socialnet

import cap.scalasmt._
import cap.jeeves._
import cap.jeeves.socialnet._
import SocialNetBackend._

import org.scalatest.FunSuite
import org.scalatest.Assertions.{expect}

class TestUser extends FunSuite {
  /*
  test ("[test name]") {
    expect([value you expect]) {
      [code that should produce that value]
    }
  }
  */
  val userOne = User("one", Name("one"), Password("one"), Email("one@example.com"),
                     Birthday(1, 1, 1), Network("one"))
  val userTwo = User("two", Name("two"), Password("two"), Email("two@example.com"),
                     Birthday(2, 2, 2), Network("two"))
  test("Test Email Visibility") {
    expect("....") {
      userOne.showEmail(SocialNetContext(userTwo))
    }
  }
}
