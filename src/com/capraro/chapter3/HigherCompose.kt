package com.capraro.chapter3

//Exercise 3.4
typealias IntUnaryOp = (Int) -> Int

val higherCompose: (IntUnaryOp) -> (IntUnaryOp) -> IntUnaryOp =
    { x -> { y -> { z -> x(y(z)) } } }

val square: IntUnaryOp = { it * it }
val triple: IntUnaryOp = { it * 3 }

fun main() {
    println(higherCompose(square)(triple)(2))
}