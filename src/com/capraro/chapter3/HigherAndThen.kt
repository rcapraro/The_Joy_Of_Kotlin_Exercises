package com.capraro.chapter3

fun <T, U, V> higherAndThen(): ((T) -> U) -> ((U) -> V) -> (T) -> V =
    { f -> { g -> { x -> g(f(x)) } } }

fun main() {
    val f: (Double) -> Int = { a -> (a * 3).toInt() }
    val g: (Long) -> Double = { a -> a + 2.0 }

    check(higherAndThen<Long, Double, Int>()(g)(f)(2) == 5)
}