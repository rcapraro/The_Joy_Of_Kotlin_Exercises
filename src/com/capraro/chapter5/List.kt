package com.capraro.chapter5

sealed class List<out A> {
    abstract fun isEmpty(): Boolean

    abstract fun drop(n: Int): List<A>

    abstract fun dropWhile(p: (A) -> Boolean): List<A>

    fun cons(a: @UnsafeVariance A): List<A> = Cons(a, this)

    fun setHead(a: @UnsafeVariance A): List<A> = when (this) {
        is Nil -> throw IllegalArgumentException("cannot call setHead on a NIL List")
        is Cons -> tail.cons(a)
    }

    fun reverse(): List<A> = reverse(invoke(), this)

    fun init(): List<A> = reverse().drop(1).reverse()

    private object Nil : List<Nothing>() {
        override fun isEmpty() = true

        override fun drop(n: Int) = this

        override fun dropWhile(p: (Nothing) -> Boolean) = this

        override fun toString(): String = "[NIL]"

        override fun equals(other: Any?): Boolean {
            return other is Nil
        }
    }

    private class Cons<A>(
        internal val head: A,
        internal val tail: List<A>
    ) : List<A>() {
        override fun isEmpty() = false

        override fun drop(n: Int): List<A> {
            tailrec fun drop(n: Int, acc: List<A>): List<A> =
                if (n <= 0) acc else when (acc) {
                    is Nil -> acc
                    is Cons -> drop(n - 1, acc.tail)
                }
            return drop(n, this)
        }

        override fun dropWhile(p: (A) -> Boolean): List<A> {
            tailrec fun dropWhile(p: (A) -> Boolean, acc: List<A>): List<A> =
                when (acc) {
                    Nil -> acc
                    is Cons -> if (p(acc.head)) dropWhile(p, acc.tail) else acc
                }
            return dropWhile(p, this)
        }

        override fun toString(): String = "[${toString("", this)}NIL]"

        private tailrec fun toString(acc: String, list: List<A>): String =
            when (list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }

        override fun equals(other: Any?): Boolean =
            when (other) {
                Nil -> false
                is Cons<*> -> this.head == other.head && this.tail == other.tail
                else -> false
            }

        override fun hashCode(): Int {
            var result = head?.hashCode() ?: 0
            result = 31 * result + tail.hashCode()
            return result
        }
    }

    companion object {
        operator fun <A> invoke(vararg az: A): List<A> =
            az.foldRight(Nil as List<A>) { a: A, list: List<A> ->
                Cons(a, list)
            }

        tailrec fun <A> reverse(acc: List<A>, list: List<A>): List<A> =
            when (list) {
                is Nil -> acc
                is Cons -> reverse(acc.cons(list.head), list.tail)
            }

        fun sum(ints: List<Int>): Int =
            when (ints) {
                Nil -> 0
                is Cons -> ints.head + sum(ints.tail)
            }
    }
}

fun main() {
    val list = List('a', 'b', 'c', 'd')
    check(list.reverse() == List('d', 'c', 'b', 'a'))
    check(list.init() == List('a', 'b', 'c'))
    check(List.sum(List(1, 2, 3, 4)) == 10)
}