package com.capraro.chapter5

sealed class List<A> {
    abstract fun isEmpty(): Boolean

    abstract fun drop(n: Int): List<A>

    fun cons(a: A): List<A> = Cons(a, this)

    fun setHead(a: A): List<A> = when (this) {
        is Nil -> throw IllegalArgumentException("cannot call setHead on a NIL List")
        is Cons -> tail.cons(a)
    }

    private object Nil : List<Nothing>() {
        override fun isEmpty() = true
        override fun drop(n: Int) = this
        override fun toString(): String = "[NIL]"
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

        override fun toString(): String = "[${toString("", this)}NIL]"
        private tailrec fun toString(acc: String, list: List<A>): String =
            when (list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }
    }

    companion object {
        operator
        fun <A> invoke(vararg az: A): List<A> = //
            az.foldRight(Nil as List<A>) { a: A, list: List<A> ->
                Cons(a, list)
            }
    }
}