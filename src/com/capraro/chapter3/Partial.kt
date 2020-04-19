package com.capraro.chapter3

//Exercise 3.7
fun <A, B, C> partialA(a: A, f: (A) -> (B) -> C): (B) -> C = f(a)

//Exercise 3.8
fun <A, B, C> partialB(b: B, f: (A) -> (B) -> C): (A) -> C = { a: A -> f(a)(b) }

fun main() {
    val f = { a: Int -> { b: Double -> a * (1 + b / 100) } }
    val partial = partialA<Int, Double, Double>(2, f)

    println(partial(50.0))
}