package week2.funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  test("adding ints") {
    assert(1 + 2 === 3)
  }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    *   val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val sbound = singletonSet(1000)
    def valueFilter(x: Int)(a: Int):Boolean = a == x
  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only elements in both") {
    new TestSets {
      val s = union(s1, s2)
      val t = union(s2, s3)
      val result = intersect(s, t)
      assert(contains(result, 2), "Intersect contains")
      assert(!contains(result, 1), "should not contain 1")
      assert(!contains(result, 3), "should not contain 3")
    }
  }

  test("diff(s,t) contains elements in s not not in t") {
    new TestSets {
      val s = union(s1, s2)
      val t = union(s2, s3)
      val result = diff(s, t)
      assert(contains(result, 1), "Intersect contains")
      assert(!contains(result, 2), "should not contain 1")
      assert(!contains(result, 3), "should not contain 3")
    }
  }

  test("set filter") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      //      val result = filter(s, (x: Int) => x == 1)
      //      def valueFilter(x: Int)(a: Int):Boolean = a == x
      assert(contains(filter(s, valueFilter(1)), 1), "filter contains 1 ")
      assert(!contains(filter(s, valueFilter(49)), 49), "filter not contains 1 ")
      assert(contains(filter(s, (x: Int) => x % 2 ==0), 2), "filter contains 2 ")

    }
  }

  test("set forall") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(forall(s,(x: Int) => x > 0), "all integers are positive")
      assert(!forall(s,(x: Int) => x < 0), "all integers not positive")
      assert(forall(s,(x: Int) => x < 1000), "all integers are less than bound")
      assert(!forall(s,valueFilter(1)), "not all values are 1")
      assert(forall(s1,valueFilter(1)), "all integers are positive")

    }
  }

  test("set exists") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      assert(exists(s,(x: Int) => x > 0), "all integers are positive")
      assert(!exists(s,(x: Int) => x < 0), "all integers are positive")
      assert(exists(s,valueFilter(1)), "value 1 exists in s")
      assert(exists(s1,valueFilter(1)), "value 1 exists in s1")
      assert(!exists(s1,valueFilter(2)), "value 2 does not exist in s1")
    }
  }

  test("map") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      def double = (x: Int) => x*2
      val r = map(s1,double)
      assert( contains(r,2), "value 2 exists in mapped s1")
      assert(!contains(r,3), "value 3 does not exist in mapped s1")
      val rs = map(s, double)
      assert(contains(rs,2) && contains(rs, 4) && contains(rs, 6) && !(contains(rs,1)), "value 2,4,6 exist in mapped s")
      assert(contains(map(sbound, double), 2000), "should contain 2000")
    }
  }

}

