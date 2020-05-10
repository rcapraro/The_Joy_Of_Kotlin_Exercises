package com.capraro.chapter3

fun <A, B, C> partialA(a: A, f: (A) -> (B) -> C): (B) -> C = f(a)

fun <A, B, C> partialB(b: B, f: (A) -> (B) -> C): (A) -> C = { a: A -> f(a)(b) }

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

    check(partialFirst(50.0) == partialSecond(2))
    check(swapArgs(f)(50.0)(2) == f(2)(50.0))
}