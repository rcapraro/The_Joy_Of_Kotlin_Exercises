package com.capraro.chapter3

fun <T, U, V> higherComposePolyMorphic(): ((U) -> V) -> ((T) -> U) -> (T) -> V =
    { f -> { g -> { x -> f(g(x)) } } }

fun main() {
    val squareP: (Int) -> Int = { it * it }
    val tripleP: (Int) -> Int = { it * 3 }

    println(higherComposePolyMorphic<Int, Int, Int>()(squareP)(tripleP)(2))
    println(higherComposePolyMorphic<Double, Double, Double>()() { x: Double -> Math.PI / 2 - x }(Math::sin)(2.0))
}