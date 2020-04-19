package com.capraro.chapter3

//Exercise 3.3
typealias IntBinOp = (Int) -> (Int) -> Int

val add: IntBinOp = { a -> { b -> a + b } }
val mult: IntBinOp = { a -> { b -> a * b } }

fun main() {
    println(add(3)(5))
    println(mult(2)(8))
}