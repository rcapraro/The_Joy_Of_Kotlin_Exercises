package com.capraro.chapter3

//Exercise 3.9
fun <A, B, C, D> func(a: A, b: B, c: C, d: D): String = "$a, $b, $c, $d"

//Exercise 3.10
fun <A, B, C, D> curried(): (A) -> (B) -> (C) -> (D) -> String =
    { a: A ->
        { b: B ->
            { c: C ->
                { d: D ->
                    "$a, $b, $c, $d"
                }
            }
        }
    }

fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =
    { a -> { b -> f(a, b) } }

fun main() {
    println(curried<String, String, String, String>()("Hello")("World")("How are you")("Today ?"))

    val sum: (Int, Int) -> Int = { a, b -> a + b }

    val curriedSum3 = curry(sum)(3)

    println(curriedSum3(2))
}
