package com.capraro.chapter4

import java.math.BigInteger

fun fibo(number: Int): String {
    tailrec fun fibo(acc: List<BigInteger>, val1: BigInteger, val2: BigInteger, x: BigInteger): List<BigInteger> =
        when (x) {
            BigInteger.ZERO -> acc
            BigInteger.ONE -> acc + (val1 + val2)
            else -> fibo(acc + (val1 + val2), val2, val1 + val2, x - BigInteger.ONE)
        }
    return makeString(fibo(listOf(), BigInteger.ZERO, BigInteger.ONE, BigInteger.valueOf(number.toLong())), ", ")
}

fun main() {
    check(fibo(5) == "1, 2, 3, 5, 8")
}