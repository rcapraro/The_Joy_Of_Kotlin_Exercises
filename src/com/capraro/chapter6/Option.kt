package com.capraro.chapter6

import com.capraro.chapter5.List
import com.capraro.chapter6.Option.Companion.map2

sealed class Option<out A> {

    abstract fun isEmpty(): Boolean

    abstract fun <B> map(f: (A) -> B): Option<B>

    internal object None : Option<Nothing>() {
        override fun isEmpty() = true
        override fun toString(): String = "None"
        override fun equals(other: Any?): Boolean = other === None
        override fun hashCode(): Int = 0
        override fun <B> map(f: (Nothing) -> B): Option<B> = None
    }

    internal data class Some<out A>(internal val value: A) : Option<A>() {
        override fun isEmpty() = false

        override fun <B> map(f: (A) -> B): Option<B> = Some(f(value))

    }

    fun getOrElse(default: () -> @UnsafeVariance A): A = when (this) {
        is None -> default()
        is Some -> value
    }

    fun orElse(default: () -> Option<@UnsafeVariance A>): Option<A> =
        map { this }.getOrElse(default)

    fun <B> flatMap(f: (A) -> Option<B>): Option<B> = map(f).getOrElse { None }

    fun filter(p: (A) -> Boolean): Option<A> =
        flatMap { if (p(it)) this else None }

    fun <A, B> lift(f: (A) -> B): (Option<A>) -> Option<B> =
        { it.map(f) }

    companion object {
        operator fun <A> invoke(a: A? = null): Option<A> = when (a) {
            null -> None
            else -> Some(a)
        }

        fun <A, B, C> map2(oa: Option<A>, ob: Option<B>, f: (A) -> (B) -> C): Option<C> =
            oa.flatMap { a ->
                ob.map { b -> f(a)(b) }
            }
    }
}

fun <A, B> traverse(list: List<A>, f: (A) -> Option<B>): Option<List<B>> =
    list.foldRight(Option(List())) { x ->
        { y: Option<List<B>> ->
            map2(f(x), y) { a ->
                { b: List<B> ->
                    b.cons(a)
                }
            }
        }
    }

fun <A> sequence(list: List<Option<A>>): Option<List<A>> =
    traverse(list) { x -> x }

