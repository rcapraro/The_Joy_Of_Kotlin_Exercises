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

    private tailrec fun <A> reverse(acc: List<A>, list: List<A>): List<A> =
        when (list) {
            is Nil -> acc
            is Cons -> reverse(acc.cons(list.head), list.tail)
        }

    fun reverse(): List<A> = this.reverse(invoke(), this)

    fun init(): List<A> = this.reverse().drop(1).reverse()

    private fun <B> foldRight(identity: B, f: (A) -> (B) -> B): B = foldRight(this, identity, f)

    private fun <B> foldLeft(identity: B, f: (B) -> (A) -> B): B = foldLeft(identity, this, f)

    private fun <A, B> foldRight(list: List<A>, identity: B, f: (A) -> (B) -> B): B =
        when (list) {
            Nil -> identity
            is Cons -> f(list.head)(foldRight(list.tail, identity, f))
        }

    private fun <A, B> foldLeft(acc: B, list: List<A>, f: (B) -> (A) -> B): B =
        when (list) {
            Nil -> acc
            is Cons -> foldLeft(f(acc)(list.head), list.tail, f)
        }

    fun lengthFoldRight(): Int = foldRight(0) { { it + 1 } }

    fun lengthFoldLeft(): Int = foldLeft(0) { i -> { i + 1 } }

    fun reverseFoldLeft(): List<A> =
        foldLeft(Nil as List<A>) { acc -> { acc.cons(it) } }

    private fun <A, B> foldRightViaFoldLeft(list: List<A>, identity: B, f: (A) -> (B) -> B): B =
        list.reverse().foldLeft(identity) { x -> { y -> f(y)(x) } }

    fun <B> map(f: (A) -> B): List<B> =
        this.foldRight(Nil as List<B>) { h ->
            { t ->
                t.cons(f(h))
            }
        }

    fun filter(f: (A) -> Boolean): List<A> =
        this.foldRight(Nil as List<A>) { h ->
            { t ->
                if (f(h)) Cons(h, t) else t
            }
        }

    fun <B> flatMap(f: (A) -> List<B>): List<B> = flatten(map(f))

    fun filterWithFlatmap(f: (A) -> Boolean): List<A> =
        flatMap { a -> if (f(a)) invoke(a) else Nil }

    object Nil : List<Nothing>() {
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

        fun <A> concat(list1: List<A>, list2: List<A>): List<A> = when (list1) {
            Nil -> list2
            is Cons -> concat(list1.tail, list2).cons(list1.head)
        }

        fun <A> concatViaFoldRight(list1: List<A>, list2: List<A>): List<A> =
            list1.foldRight(list2) { x -> { y -> Cons(x, y) } }

        fun <A> concatViaFoldLeft(list1: List<A>, list2: List<A>): List<A> =
            list1.reverse().foldLeft(list2) { x -> { y: A -> Cons(y, x) } }

        fun sumFoldRight(list: List<Int>): Int =
            list.foldRight(list, 0) { x -> { y -> x + y } }

        fun productFoldRight(list: List<Double>): Double =
            list.foldLeft(1.0, list) { x -> { y -> x * y } }

        fun sumFoldLeft(list: List<Int>): Int =
            list.foldRight(list, 0) { x -> { y -> x + y } }

        fun productFoldLeft(list: List<Double>): Double =
            list.foldRight(list, 1.0) { x -> { y -> x * y } }

        fun <A> flatten(list: List<List<A>>): List<A> = list.foldRight(Nil) { x -> { y: List<A> -> concat(x, y) } }

        fun triple(list: List<Int>): List<Int> =
            list.foldRight(invoke()) { h ->
                { t: List<Int> ->
                    t.cons(h * 3)
                }
            }
    }
}

fun main() {
    val list1 = List('a', 'b', 'c', 'd')
    val list2 = List('1', '2', '3')
    check(list1.reverse() == List('d', 'c', 'b', 'a'))
    check(list1.reverseFoldLeft() == List('d', 'c', 'b', 'a'))
    check(list1.init() == List('a', 'b', 'c'))
    check(List.sumFoldRight(List(1, 2, 3, 4)) == 10)
    check(List.sumFoldLeft(List(1, 2, 3, 4)) == 10)
    check(List.productFoldRight(List(2.0, 3.0, 4.0)) == 24.0)
    check(List.productFoldLeft(List(2.0, 3.0, 4.0)) == 24.0)
    check(list1.lengthFoldLeft() == 4)
    check(list1.lengthFoldRight() == 4)
    check(list1.setHead('k') == List('k', 'b', 'c', 'd'))
    check(List.concat(list1, list2) == List('a', 'b', 'c', 'd', '1', '2', '3'))
    check(List.concat(list1, list2) == List.concatViaFoldRight(list1, list2))
    check(List.concat(list1, list2) == List.concatViaFoldRight(list1, list2))
    check(List.flatten(List(list1, list2)) == List('a', 'b', 'c', 'd', '1', '2', '3'))
    check(List.triple(List(1, 2, 3)) == List(3, 6, 9))
    check(list2.map { it.toString().toInt() } == List(1, 2, 3))
    check(list2.flatMap { it -> List(it.toString().toInt() + 10, it.toString().toInt() + 100) } == List(
        11,
        101,
        12,
        102,
        13,
        103
    ))
}
