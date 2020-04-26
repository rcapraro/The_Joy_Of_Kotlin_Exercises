package com.capraro.chapter4

fun <T, U> foldLeft(list: List<T>, seed: U, f: (U, T) -> U): U {
    tailrec fun foldLeft_(list: List<T>, acc: U): U = when {
        list.isEmpty() -> acc
        else -> foldLeft_(list.tail(), f(acc, list.head()))
    }
    return foldLeft_(list, seed)
}

fun sumF(list: List<Int>): Int = foldLeft(list, 0, Int::plus)
fun stringF(list: List<String>): String = foldLeft(list, "", String::plus)
fun <T> makeStringF(list: List<T>, delim: String): String = foldLeft(list, "") { s, t ->
    when {
        s.isEmpty() -> "$t"
        else -> "$s$delim$t"
    }
}