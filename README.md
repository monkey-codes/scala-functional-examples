# Functional programming examples using Scala

## Recursion
### Pascal's Triangle
```
    1
   1 1
  1 2 1
 1 3 3 1
1 4 6 4 1
```
[Calculates Pascal's Triangle](src/main/scala/week1/recursion/Pascal.scala) given a valid column and row.
A [Test Suite](src/test/scala/week1/recursion/PascalSuite.scala) is also included.

### Parentheses Balancing
A [recursive function](src/main/scala/week1/recursion/Balance.scala) ( [Test Suite](src/test/scala/week1/recursion/BalanceSuite.scala)) that tests whether the parentheses in a string are balanced. 
Valid cases include:
*  `hello(world())`
* `some text with (some content in brackets).

Invalid cases:
* :-(
* )(()

### Calculating Change (as in for money)
Given a set of possible coin denominations, [count all](src/main/scala/week1/recursion/ChangeCounter.scala) 
([Test Suite](src/test/scala/week1/recursion/ChangeCounterSuite.scala)) the possible ways to give change for a given amount x.
```
Example:
denominations: 1,2
change amount: 4
solutions: [1+1+1+1, 2+2, 1+1+2]
function return value: 3 - (3 possible solutions)
```
