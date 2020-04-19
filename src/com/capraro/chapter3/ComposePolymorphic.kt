package com.capraro.chapter3

//Exercise 3.2
fun <T, U, V> compose(f: (U) -> V, g: (T) -> U): (T) -> V = { f(g(it)) }

fun main() {
    val f: (Double) -> Int = { a -> (a * 3).toInt() }
    val g: (Long) -> Double = { a -> a + 2.0 }

    check(compose(f, g)(2) == 12)
}