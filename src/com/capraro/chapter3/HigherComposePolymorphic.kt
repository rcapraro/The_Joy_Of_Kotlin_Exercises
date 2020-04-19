package com.capraro.chapter3

fun <T, U, V> higherComposePolyMorphic(): ((U) -> V) -> ((T) -> U) -> (T) -> V =
    { f -> { g -> { x -> f(g(x)) } } }

fun main() {
    val squareP: (Int) -> Int = { it * it }
    val tripleP: (Int) -> Int = { it * 3 }

    check(higherComposePolyMorphic<Int, Int, Int>()(squareP)(tripleP)(2) == 36)
    check(higherComposePolyMorphic<Double, Double, Double>()() { x: Double -> x / 2 }(Math::cos)(0.0) == 0.5)
}