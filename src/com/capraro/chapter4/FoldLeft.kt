package com.capraro.chapter4

fun <T, U> foldLeft(list: List<T>, seed: U, f: (U, T) -> U): U {
    tailrec fun foldLeft(list: List<T>, acc: U): U = when {
        list.isEmpty() -> acc
        else -> foldLeft(list.tail(), f(acc, list.head()))
    }
    return foldLeft(list, seed)
}

fun sumFoldLeft(list: List<Int>): Int = foldLeft(list, 0, Int::plus)

fun stringFoldLeft(list: List<Char>): String = foldLeft(list, "", String::plus)

fun <T> makeStringFoldLeft(list: List<T>, delim: String): String = foldLeft(list, "") { s, t ->
    when {
        s.isEmpty() -> "$t"
        else -> "$s$delim$t"
    }
}

fun main() {
    check(sumFoldLeft(listOf(1, 2, 3)) == 6)
    check(stringFoldLeft(listOf('h', 'e', 'l', 'l', 'o')) == "hello")
    check(makeStringFoldLeft(listOf(1, 2, 3, 4, 5), "<") == "1<2<3<4<5")
}