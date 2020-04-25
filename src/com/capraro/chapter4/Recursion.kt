package com.capraro.chapter4

import java.math.BigInteger

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

fun <T> List<T>.head(): T =
    if (this.isEmpty())
        throw IllegalArgumentException("head called on empty list")
    else
        this[0]

fun <T> List<T>.tail(): List<T> =
    if (this.isEmpty())
        throw IllegalArgumentException("tail called on empty list")
    else
        this.drop(1)


fun <T> makeString(list: List<T>, delim: String): String {
    tailrec fun makeString_(list: List<T>, acc: String): String = when {
        list.isEmpty() -> acc
        acc.isEmpty() -> makeString_(list.tail(), "$acc$delim${list.head()}")
        else -> makeString_(list.tail(), "$acc$delim${list.head()}")
    }
    return makeString_(list, "")
}


fun main() {
    check(Factorial.factorial(4) == 24)
    check(fibonacci(46) == 2_971_215_073.toBigInteger())

    println(makeString(listOf(1, 2, 3, 4, 5), "<"))
}