package com.capraro.chapter4

fun <T, U> foldRight(list: List<T>, identity: U, f: (T, U) -> U): U =
    if (list.isEmpty()) identity
    else f(list.head(), foldRight(list.tail(), identity, f))

fun stringFoldRight(list: List<Char>): String = foldRight(list, "") { c, s -> prepend(c, s) }

fun main() {
    check(stringFoldRight(listOf('h', 'e', 'l', 'l', 'o')) == "hello")
}