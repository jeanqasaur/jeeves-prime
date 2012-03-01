package test.cap.jeeves.pat

import cap.jeeves.pat._

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
  val userOne = User(Username("one"), Name("one"), Password("one"), Email("one@example.com"),
                     Birthday(1, 1, 1), Network("one"))
  val userTwo = User(Username("two"), Name("two"), Password("two"), Email("two@example.com"),
                     Birthday(2, 2, 2), Network("two"))
  val userTen = User(Username("ten"), Name("ten"), Password("ten"), Email("ten@example.com"),
                     Birthday(10, 10, 10), Network("one"))
  test("Test isSelf") {
    expect(true) {
      userOne.showSelf(SocialNetContext(userOne))
    }
    expect(false) {
      userOne.showSelf(SocialNetContext(userTen))
    }
  }
  test("User Name Visibility") {
    expect("one") {
      userOne.showUsername(SocialNetContext(userTwo))
    }
  }
  test("Test Email Visibility") {
    expect("....") {
      userOne.showEmail(SocialNetContext(userTwo))
    }
    expect("one@example.com") {
      userOne.showEmail(SocialNetContext(userOne))
    }
  }
}
