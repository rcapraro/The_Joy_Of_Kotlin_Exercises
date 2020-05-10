package com.capraro.chapter4

fun append(s: String, c: Char): String = "$s$c"

fun toStringCoRecursion(list: List<Char>, s: String): String =
    if (list.isEmpty()) s
    else toStringCoRecursion(list.tail(), append(s, list.head()))


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
    check(toStringCoRecursion(listOf('h', 'e', 'l', 'l', 'o'), "") == "hello")
    check(addLoop(3, 4) == 7)
    check(add(3, 4) == 7)
}