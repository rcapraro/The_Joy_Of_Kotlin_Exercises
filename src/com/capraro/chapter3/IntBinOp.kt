package com.capraro.chapter3

typealias IntBinOp = (Int) -> (Int) -> Int

val add: IntBinOp = { a -> { b -> a + b } }
val mult: IntBinOp = { a -> { b -> a * b } }

fun main() {
    check(add(3)(5) == 8)
    check(mult(2)(8) == 16)
}