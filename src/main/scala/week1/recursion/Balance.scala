package week1.recursion

object Balance {

  def balance(chars: List[Char]): Boolean = {

    def balance(open: Int, head: Char, tail: List[Char]): Int = {
      if (open < 0) return open
      if (tail.isEmpty && head == '(') return open + 1
      if (tail.isEmpty && head == ')') return open - 1
      if (tail.isEmpty) return open
      if (head == '(') return balance(open + 1, tail.head, tail.tail)
      if (head == ')') return balance(open - 1, tail.head, tail.tail)
      return balance(open, tail.head, tail.tail)
    }

    balance(0, chars.head, chars.tail) == 0
  }

}