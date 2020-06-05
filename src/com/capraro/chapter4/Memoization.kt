package com.capraro.chapter4

import java.math.BigInteger

fun fibo(number: Int): String {
    tailrec fun fibo(acc: List<BigInteger>, acc1: BigInteger, acc2: BigInteger, x: BigInteger): List<BigInteger> =
        when (x) {
            BigInteger.ZERO -> acc
            BigInteger.ONE -> acc + (acc1 + acc2)
            else -> fibo(acc + (acc1 + acc2), acc2, acc1 + acc2, x - BigInteger.ONE)
        }
    return makeString(fibo(listOf(), BigInteger.ZERO, BigInteger.ONE, BigInteger.valueOf(number.toLong())), ", ")
}

fun <T> iterate(seed: T, f: (T) -> T, n: Int): List<T> {
    tailrec fun iterate(acc: List<T>, seed: T): List<T> =
        if (acc.size < n)
            iterate(acc + seed, f(seed))
        else
            acc
    return iterate(listOf(), seed)
}

fun <T, U> map(list: List<T>, f: (T) -> U): List<U> {
    fun map(acc: List<U>, list: List<T>): List<U> =
        when {
            list.isEmpty() -> acc
            else -> map(acc + f(list.head()), list.tail())
        }
    return map(listOf(), list)
}

fun <T, U> mapFoldLeft(list: List<T>, f: (T) -> U): List<U> = foldLeft(list, listOf()) { lst, elem -> lst + f(elem) }


fun fiboCorecursive(n: Int): String {
    val seed = BigInteger.ONE to BigInteger.valueOf(2)
    val f = { pair: Pair<BigInteger, BigInteger> -> pair.second to (pair.first + pair.second) }
    val fibonacciPairs = iterate(seed, f, n)
    val fibonacciNumbers = map(fibonacciPairs) { it.first }
    return makeString(fibonacciNumbers, ", ")
}

fun main() {
    check(fibo(5) == "1, 2, 3, 5, 8")
    check(iterate(2, { it * 2 }, 10) == listOf(2, 4, 8, 16, 32, 64, 128, 256, 512, 1024))
    check(map(listOf("x", "xx", "xxx")) { it.length } == listOf(1, 2, 3))
    check(mapFoldLeft(listOf("x", "xx", "xxx")) { it.length } == listOf(1, 2, 3))
    check(fiboCorecursive(5) == "1, 2, 3, 5, 8")
}