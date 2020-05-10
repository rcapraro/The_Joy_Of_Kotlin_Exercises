package com.capraro.chapter4

import java.math.BigInteger

fun prepend(c: Char, s: String): String = "$c$s"

fun toStringRecursion(list: List<Char>): String =
    if (list.isEmpty()) ""
    else prepend(list.head(), toStringRecursion(list.tail()))

object Factorial {
    val factorial: (Int) -> Int by lazy {
        { n: Int ->
            if (n <= 1) n else n * factorial(n - 1)
        }
    }
}

/*
- f (0) = 1
- f (1) = 1
- f (n) = f (n – 1) + f (n – 2)
 */
fun fibonacci(n: Int): BigInteger {
    tailrec fun go(val1: BigInteger, val2: BigInteger, x: BigInteger): BigInteger = when {
        (x == BigInteger.ZERO) -> BigInteger.ONE
        (x == BigInteger.ONE) -> val1 + val2
        else -> go(val2, val1 + val2, x - BigInteger.ONE)
    }
    return go(BigInteger.ZERO, BigInteger.ONE, n.toBigInteger())
}

fun sum(list: List<Int>): Int {
    tailrec fun sum(list: List<Int>, acc: Int): Int = when {
        list.isEmpty() -> acc
        else -> sum(list.tail(), acc + list.head())
    }
    return sum(list, 0)
}

fun <T> makeString(list: List<T>, delim: String): String {
    tailrec fun makeString(list: List<T>, acc: String): String = when {
        list.isEmpty() -> acc
        acc.isEmpty() -> makeString(list.tail(), "${list.head()}")
        else -> makeString(list.tail(), "$acc$delim${list.head()}")
    }
    return makeString(list, "")
}

fun main() {
    check(toStringRecursion(listOf('h', 'e', 'l', 'l', 'o')) == "hello")
    check(Factorial.factorial(4) == 24)
    check(fibonacci(46) == 2_971_215_073.toBigInteger())
    check(sum(listOf(1, 2, 3, 4, 5)) == 15)
    check(makeString(listOf(1, 2, 3, 4, 5), "<") == "1<2<3<4<5")
}