package com.capraro.chapter4

fun range(start: Int, end: Int): List<Int> {
    val result = mutableListOf<Int>()
    var index = start
    while (index < end) {
        result.add(index)
        index++
    }
    return result
}

fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    var elem = seed
    while (p(elem)) {
        result.add(elem)
        elem = f(elem)
    }
    return result
}

fun rangeUnfold(start: Int, end: Int): List<Int> = unfold(start, { it + 1 }, { it < end })

fun <T> coRecursiveUnfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> =
    if (p(seed))
        prepend(unfold(f(seed), f, p), seed)
    else
        listOf()

fun <T> recursiveUnfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    tailrec fun recursiveUnfold(acc: List<T>, seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> =
        if (p(seed))
            recursiveUnfold(acc + seed, f(seed), f, p)
        else
            acc
    return recursiveUnfold(listOf(), seed, f, p)
}


fun main() {
    check(range(1, 4) == listOf(1, 2, 3))
    check(rangeUnfold(1, 4) == listOf(1, 2, 3))
    check(coRecursiveUnfold(1, { it + 1 }, { it < 4 }) == listOf(1, 2, 3))
    check(recursiveUnfold(1, { it + 1 }, { it < 4 }) == listOf(1, 2, 3))
}

