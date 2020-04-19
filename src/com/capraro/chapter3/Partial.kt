package com.capraro.chapter3

//Exercise 3.7
fun <A, B, C> partialA(a: A, f: (A) -> (B) -> C): (B) -> C = f(a)

//Exercise 3.8
fun <A, B, C> partialB(b: B, f: (A) -> (B) -> C): (A) -> C = { a: A -> f(a)(b) }

//Excercise 3.11
fun <A, B, C> swapArgs(f: (A) -> (B) -> C): (B) -> (A) -> C =
    { b ->
        { a ->
            f(a)(b)
        }
    }

fun main() {
    val f = { a: Int -> { b: Double -> a * (1 + b / 100) } }
    val partialFirst = partialA<Int, Double, Double>(2, f)
    val partialSecond = partialB<Int, Double, Double>(50.0, f)

    assert(partialFirst(50.0) == partialSecond(2))
    assert(swapArgs(f)(50.0)(2) == f(2)(50.0))
}