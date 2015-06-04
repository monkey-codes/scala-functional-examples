package week4.patternmatching


import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times") {
    val t = times(string2Chars("hello"))
    assert(t.contains(('l',2)))
    assert(t.contains(('h',1)))
    assert(t.contains(('e',1)))
    assert(t.contains(('o',1)))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    val c = combine(leaflist)
    assert(c === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("until") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    val c = until(singleton, combine)(leaflist)
    assert(c.size === 1)
    assert(weight(c.head) === 7)
  }

  test("createCodeTree") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    val codeTree = createCodeTree(string2Chars("xxxxtte"))
    assert(weight(codeTree) === 7)
  }

  test("decodeFrench") {
    val result = decode(frenchCode, secret)
    println(result.mkString)
    println(result)
  }

  test("simpleDecode") {
    new TestTrees {
      val result = decode(t1,List(0,1,0))
      assert(result === List('a','b','a'))
    }
  }

  test("encodeFrench") {
    val result = encode(frenchCode)("huffmanestcool".toList)
    assert(result === secret)
    //    println(result.mkString)
    //    println(result)
  }

  test("simpleEncode") {
    new TestTrees {
      val result = encode(t1)(List('a','b','a'))
      assert(result === List(0,1,0))
    }
  }


  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("testMergeCodeTable") {
    new TestTrees {
      val x = List(('a',List(0)))
      val y = List(('b',List(1)))
      assert(mergeCodeTables(x, y) === List(('a',List(0,0)),('b', List(1,1))))
    }
  }

  test("testConvert") {
    new TestTrees {
      val x = convert(t1)
      def charPredicate(c: Char)(y: (Char, List[Bit])): Boolean = y._1 == c
      assert(x.find(charPredicate('a')).get._2 === List(0))
      assert(x.find(charPredicate('b')).get._2 === List(1))
    }
  }

  test("testCodeBits") {
    new TestTrees {
      val codeTable = List(('a', List(0,0)), ('b', List(0,1)))
      def table = codeBits(codeTable)_
      assert(table('a') === List(0,0))
      assert(table('b') === List(0,1))
    }
  }

  test("quickEncodeFrench") {
    val result = quickEncode(frenchCode)("huffmanestcool".toList)
    assert(result === secret)
  }


}

