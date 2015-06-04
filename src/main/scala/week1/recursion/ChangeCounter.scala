package week1.recursion

/**
  * Created by johan on 10/5/17.
  */
object ChangeCounter {

  def countChange(money: Int, coins: List[Int]): Int = {
    def f(money: Int, coins: List[Int]): Int = {
      if (coins.isEmpty) 0;
      else if (money - coins.head == 0) 1
      else if (money - coins.head < 0) 0
      else f(money - coins.head, coins) + f(money, coins.tail)
    }
    f(money, coins.sorted)
  }

}
