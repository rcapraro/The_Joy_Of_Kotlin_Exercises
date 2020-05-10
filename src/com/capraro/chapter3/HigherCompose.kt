package com.capraro.chapter3

typealias IntUnaryOp = (Int) -> Int

val higherCompose: (IntUnaryOp) -> (IntUnaryOp) -> IntUnaryOp =
    { x -> { y -> { z -> x(y(z)) } } }

fun main() {
    val square: IntUnaryOp = { it * it }
    val triple: IntUnaryOp = { it * 3 }

    check(higherCompose(square)(triple)(2) == 36)
}