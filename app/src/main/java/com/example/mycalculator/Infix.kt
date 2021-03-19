package com.example.mycalculator

const val ADD = "+"
const val SUB = "-"
const val MUL = "*"
const val DIV = "/"
const val NAN = "NaN"
const val EQUAL = "="
const val DOT = "."
const val ZERO = "0"
const val OPEN_PARENTHESES = "("
const val CLOSE_PARENTHESES = ")"
const val BLANK = ""

class Infix() {
    private val stackE = Stack<String>()

    fun getResult(expression: String): String {
        val postfix = toPostfix(expression)

        postfix ?: return NAN
        for (character in postfix) {
            if (character in listOf<String>(ADD, SUB, MUL, DIV)) {
                calPostfix(character).also {
                    it ?: return NAN
                    stackE.push(it.toString())
                }

            } else {
                stackE.push(character)
            }
        }

        stackE.pop().toString().split(DOT).let {
            if (it.size == 2) {
                if (it[1].equals(ZERO)) return it.first() else return it.first() + DOT + it[1]
            } else {
                return it.first()
            }
        }

        return NAN
    }

    fun toPostfix(expression: String): MutableList<String>? {
        val expression1 = splitExpression(expression)
        val s = ArrayList<String>()

        return expression1.let {
            if (it != null) {
                for (i in it) {
                    when (i) {
                        OPEN_PARENTHESES -> stackE.push(OPEN_PARENTHESES)
                        CLOSE_PARENTHESES -> popParenthesis()?.let { s.add(it) }
                        MUL, DIV -> s.addAll(popPriority2(i))
                        ADD, SUB -> s.addAll(popPriority1(i))
                        else -> s.add(i)
                    }
                }
                for (i in stackE.stack.size downTo 1) {
                    stackE.pop()?.let { s.add(it) }
                }
                return s
            } else {
                return null
            }
        }
    }

    fun calPostfix(o: String): Double? {
        try {
            val b = stackE.pop()?.toDouble()
            val a = stackE.pop()?.toDouble()
            when (o) {
                ADD -> return (b?.let { a?.plus(it) })
                SUB -> return (b?.let { a?.minus(it) })
                MUL -> return (b?.let { a?.times(it) })
                DIV -> return (b?.let { a?.div(it) })
            }
        } catch (e: NumberFormatException) {
            return null
        }
        return null
    }

    private fun splitExpression(expression: String): MutableList<String>? {
        val list = ArrayList<String>()
        val s = StringBuilder()
        var count = 0
        for (i in expression) {
            when (i.toString()) {
                ADD, SUB, MUL, DIV, OPEN_PARENTHESES, CLOSE_PARENTHESES -> {
                    if (!s.toString().isBlank()) list.add(s.toString())
                    list.add(i.toString())
                    s.clear()

                    when (i.toString()) {
                        OPEN_PARENTHESES -> count++
                        CLOSE_PARENTHESES -> count--
                    }
                }
                else -> s.append(i)
            }
        }

        if (!s.toString().isNullOrBlank()) {
            list.add(s.toString())
        }

        if (count == 0) return list else return null
    }


    private fun popPriority2(o: String): MutableList<String> {
        val s = ArrayList<String>()
        for (i in (stackE.stack.size - 1) downTo 0) {
            when (stackE.stack[i]) {
                MUL, DIV -> s.add(stackE.pop(i).toString())
            }
        }
        stackE.push(o)
        return s
    }

    private fun popPriority1(o: String): MutableList<String> {
        val s = ArrayList<String>()
        for (i in (stackE.stack.size - 1) downTo 0) {
            when (stackE.stack[i]) {
                MUL, DIV, ADD, SUB -> s.add(stackE.pop(i).toString())
            }
        }
        stackE.push(o)
        return s
    }

    private fun popParenthesis(): String? {
        val s = StringBuilder()
        var c: String? = null

        c = stackE.pop()
        while (c != null) {
            if (c != OPEN_PARENTHESES) {
                s.append(c)
                c = stackE.pop()
            } else {
                return s.toString()
            }
        }
        return null
    }
}

private fun String.isOperator(): Boolean = this in listOf<String>(ADD, SUB, MUL, DIV)

