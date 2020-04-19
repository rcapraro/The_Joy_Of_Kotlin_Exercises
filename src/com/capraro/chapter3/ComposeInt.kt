package com.capraro.chapter3

//Exercise 3.1
fun compose(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { f(g(it)) }

fun square(n: Int): Int = n * n

fun triple(n: Int): Int = n * 3

fun main() {
    println(compose(::square, ::triple)(2))
}