package com.example.mycalculator

class Stack<T> {
    val stack: MutableList<T>

    init {
        stack = ArrayList<T>()
    }

    fun push(e: T) = stack.add(0, e)

    fun pop(): T? {
        try {
            val e = stack[0]
            stack.removeAt(0)
            return e
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }

    fun pop(i: Int): T? {
        try {
            val e = stack[i]
            stack.removeAt(i)
            return e
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }

}