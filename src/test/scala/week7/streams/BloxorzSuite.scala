package week7.streams

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Bloxorz._

@RunWith(classOf[JUnitRunner])
class BloxorzSuite extends FunSuite {

  trait SolutionChecker extends GameDef with Solver with StringParserTerrain {
    /**
      * This method applies a list of moves `ls` to the block at position
      * `startPos`. This can be used to verify if a certain list of moves
      * is a valid solution, i.e. leads to the goal.
      */
    def solve(ls: List[Move]): Block =
      ls.foldLeft(startBlock) { case (block, move) => move match {
        case Left => block.left
        case Right => block.right
        case Up => block.up
        case Down => block.down
      }
      }
  }

  trait FiveByFive extends StringParserTerrain {
    val level =
      """ooooo
        |ooooo
        |ooooo
        |ooooo
        |ooooo""".stripMargin

    val middle = Block(Pos(2,2),Pos(2,2))
    val topLeft = Block(Pos(0,0),Pos(0,0))
  }

  trait Level1 extends SolutionChecker {
    /* terrain for level 1*/

    val level =
      """ooo-------
        |oSoooo----
        |ooooooooo-
        |-ooooooooo
        |-----ooToo
        |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
  }



  test("most basic terrain test") {
    new StringParserTerrain {
      val level =
        """STo
          |o-o
          |ooo""".stripMargin

      assert(terrain(Pos(0,0)),"0,0")
      assert(!terrain(Pos(1,1)),"1,1")
    }
  }

  test("terrain function level 1") {
    new Level1 {
      assert(terrain(Pos(0,0)), "0,0")
      assert(!terrain(Pos(4,11)), "4,11")
    }
  }

  test("findChar level 1") {
    new Level1 {
      assert(startPos == Pos(1,1))
      assert(goal == Pos(4,7))
    }
  }

  test("is block standing") {
    new Level1 {
      assert(Block(Pos(0,0),Pos(0,0)).isStanding, "0,0 - 0,0")
      assert(!Block(Pos(2,2),Pos(2,3)).isStanding, "2,2 - 2,3")
    }
  }

  test("is block legal") {
    new Level1 {
      assert(Block(Pos(0,0),Pos(0,0)).isLegal, "legal in top left")
      assert(!Block(Pos(5,0),Pos(5,0)).isLegal, "illegal standing bottom left")
      assert(!Block(Pos(0,2),Pos(0,3)).isLegal, "illegal lying right bottom left")
    }
  }

  test("neighbours") {
    new FiveByFive {

      assert(middle.neighbors.toSet === Set((middle.up,Up),(middle.right,Right),(middle.down, Down),(middle.left, Left)))
    }
  }

  test("legal neighbours") {
    new FiveByFive {
      assert(middle.legalNeighbors.toSet ===
        Set((middle.up,Up),(middle.right,Right),(middle.down, Down),(middle.left, Left)))
      assert(topLeft.legalNeighbors.toSet ===
        Set((topLeft.right,Right),(topLeft.down, Down)))
    }
  }

  test("solver is done") {
    new Level1 {
      assert(done(Block(goal,goal)))
      assert(!done(Block(startPos,startPos)))
      assert(!done(Block(startPos,startPos).right))
    }
  }



  test("neighboursWithHistory") {
    new Level1 {
      val neighboursWithHistory = neighborsWithHistory(Block(Pos(1, 1), Pos(1, 1)), List(Left, Up))
      assert(neighboursWithHistory.toSet ===
        Set(
          (Block(Pos(1, 2), Pos(1, 3)), List(Right, Left, Up)),
          (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up))))
    }
  }

  test("newNeighboursOnly") {
    new Level1 {
      val newOnly = newNeighborsOnly(
        Set(
          (Block(Pos(1, 2), Pos(1, 3)), List(Right, Left, Up)),
          (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up))).toStream,

        Set(Block(Pos(1, 2), Pos(1, 3)), Block(Pos(1, 1), Pos(1, 1))))
      assert(newOnly.toSet === Set(
        (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up))))
    }
  }

  test("from") {
    new SolutionChecker {
      val level =
        """ST
          |oo
          |oo""".stripMargin
      println(from(Stream((startBlock,List())), Set(startBlock)).toList)
    }
  }


  test("optimal solution for level 1") {
    new Level1 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 1") {
    new Level1 {
      println(solution)
      assert(solution.length == optsolution.length)
    }
  }

  test("solution matches optimal solution for level 1") {
    new Level1 {
      println(solution)
      assert(solution === optsolution)
    }
  }
}
