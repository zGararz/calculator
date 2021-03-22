package com.example.mycalculator

import android.util.Log

class Infix() {
    private val stackE = Stack<String>()

    fun getResult(expression: String): String {
        val postfix = toPostfix(expression)

        if (postfix != null) {
            for (i in postfix) {
                when (i) {
                    "+", "-", "*", "/" -> {
                        if (calPostfix(i) != null) {
                            stackE.push(postfix.toString())
                        } else {
                            return "NaN"
                        }
                    }
                    else -> stackE.push(i)
                }
            }

            stackE.pop().toString().split(".").let {
                if (it.size > 1) {
                    if (it[1].equals("0")) it.get(0) else it[0] + "." + it[1]
                } else {
                    return it[0]
                }
            }
        }

        return "NaN"
    }

    fun toPostfix(expression: String): MutableList<String>? {
        val expression1 = splitExpression(expression)
        val s = ArrayList<String>()
        Log.d("AAA", "split:${expression1.toString()}")
        return expression1.let {
            if (it != null) {
                for (i in it) {
                    when (i) {
                        "(" -> stackE.push("(")
                        ")" -> s.add(popParenthesis())
                        "*", "/" -> s.addAll(popPriority2(i))
                        "+", "-" -> s.addAll(popPriority1(i))
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
                "+" -> return (b?.let { a?.plus(it) })
                "-" -> return (b?.let { a?.minus(it) })
                "*" -> return (b?.let { a?.times(it) })
                "/" -> return (b?.let { a?.div(it) })
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
            when (i) {
                '+', '-', '*', '/' -> {
                    if (!s.toString().equals("")) list.add(s.toString())
                    list.add(i.toString())
                    s.clear()
                }
                '(', ')' -> {
                    if (!s.toString().equals("")) list.add(s.toString())
                    list.add(i.toString())
                    s.clear()
                    count++
                }
                else -> s.append(i)
            }
        }

        if (s != null && !s.toString().equals("")) {
            list.add(s.toString())
        }

        if (count % 2 == 0) return list else return null
    }


    private fun popPriority2(o: String): MutableList<String> {
        val s = ArrayList<String>()
        for (i in (stackE.stack.size - 1) downTo 0) {
            when (stackE.stack[i]) {
                "*", "/" -> s.add(stackE.pop(i).toString())
            }
        }
        stackE.push(o)
        return s
    }

    private fun popPriority1(o: String): MutableList<String> {
        val s = ArrayList<String>()
        for (i in (stackE.stack.size - 1) downTo 0) {
            when (stackE.stack[i]) {
                "*", "/", "+", "-" -> s.add(stackE.pop(i).toString())
            }
        }
        stackE.push(o)
        return s
    }

    private fun popParenthesis(): String {
        val s = StringBuilder()
        var c: String? = null

        c = stackE.pop()
        while (c != null) {
            if (c != "(") {
                s.append(c)
                c = stackE.pop()
            } else {
                return s.toString()
            }
        }
        return ""
    }
}
