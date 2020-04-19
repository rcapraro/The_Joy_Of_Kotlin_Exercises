package com.capraro.chapter3

fun <T, U, V> higherComposePolyMorphic(): ((U) -> V) -> ((T) -> U) -> (T) -> V =
    { x -> { y -> { z -> x(y(z)) } } }

val squareP: (Int) -> Int = { it * it }
val tripleP: (Int) -> Int = { it * 3 }

fun main() {
    println(higherComposePolyMorphic<Int, Int, Int>()(squareP)(tripleP)(2))
}