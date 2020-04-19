package com.capraro.chapter4

fun inc(n: Int) = n + 1
fun dec(n: Int) = n - 1

fun addLoop(a: Int, b: Int): Int {
    var x = a
    var y = b
    while (true) {
        if (y == 0) return x
        x = inc(x)
        y = dec(y)
    }
}

fun add(x: Int, y: Int): Int = if (y == 0) x else add(inc(x), dec(y))

fun main() {
    check(addLoop(3, 4) == 7)
    check(add(3, 4) == 7)
}