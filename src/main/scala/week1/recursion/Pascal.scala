package week1.recursion

/**
  * Created by johan on 10/5/17.
  */
object Pascal {

  def pascal(c: Int, r: Int): Int = {
    if (c == 0 && r == 0) return 1
    if (r == 0 && c != 0) return 0
    if (c == 0) return pascal(0, r - 1)
    pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

}
