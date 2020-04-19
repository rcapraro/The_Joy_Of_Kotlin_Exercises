package com.capraro.chapter3

//Exercise 3.2
fun <T, U, V> compose(f: (U) -> V, g: (T) -> U): (T) -> V = { f(g(it)) }