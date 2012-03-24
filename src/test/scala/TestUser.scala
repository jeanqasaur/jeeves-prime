package test.cap.primes

import cap.primes._

import org.scalatest.FunSuite
import org.scalatest.Assertions.{expect}

class TestUser extends FunSuite {
	System.setProperty("smt.home", "C:\\Program Files (x86)\\Microsoft Research\\Z3-2.18\\bin\\z3")
	/*
	test ("[test name]") {
		expect([value you expect]) {
			[code that should produce that value]
		}
	}
	*/
	val user1 = User(Username("User1"), Name("Name1"), Network("N1"))
	val user2 = User(Username("User2"), Name("Name1"), Network("N1"))
	val user3 = User(Username("User3"), Name("Name1"), Network("N2"))
	val user4 = User(Username("User4"), Name("Name1"), Network("N1"))
	val user5 = User(Username("User5"), Name("Name1"), Network("N2"))
	user1.addFriend(user2)
	user1.addFriend(user5)

	test("Name - Self") {
		expect("Name1") {
			user1.showName(SocialNetContext(user1))
		}
	}
	test("Name - Friends") {
		expect("Name1") {
			user1.showName(SocialNetContext(user2))
		}
	}
	test("Name - Not Friends") {
		expect("Anonymous") {
			user1.showName(SocialNetContext(user3))
		}
	}
	test("Name - Same Network, Not Friends") {
		expect("Name1") {
			user1.showName(SocialNetContext(user4))
		}
	}
	test("Name - Different Network, Friends") {
		expect("Name1") {
			user1.showName(SocialNetContext(user5))
		}
	}
}
