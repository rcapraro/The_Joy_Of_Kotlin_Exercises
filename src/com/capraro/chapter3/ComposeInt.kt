package com.capraro.chapter3

fun compose(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { f(g(it)) }

fun main() {
    fun square(n: Int): Int = n * n
    fun triple(n: Int): Int = n * 3

    check(compose(::square, ::triple)(2) == 36)
}