package com.mxr.example

import kotlin.system.measureNanoTime

/**
 * For learning purposes
 *
 * Two solutions:
 * 1. create Leveinshtein class
 * 2. extend CharSequence functionality
 */
class Leveinshtein {

    fun leveinshteinDistance(lhs: String, rhs: String): Int {
        if (lhs === rhs) return 0
        if (lhs.isEmpty()) return rhs.length
        if (rhs.isEmpty()) return lhs.length

        val res = Array(lhs.length){ IntArray(rhs.length)}

        for (i in 0 until lhs.length)
            res[i][0] = i

        for (j in 0 until rhs.length)
            res[0][j] = j

        var subst = 0
        for (j in 1 until rhs.length) {
            for (i in 1 until lhs.length) {
                subst = if (lhs[i] == rhs[j]) 0 else 1

                val deletion = res[i-1][j] + 1
                val insertion = res[i][j-1] +1
                val substitution = res[i-1][j-1] + subst
                res[i][j] = Math.min(Math.min(deletion, insertion), substitution)
            }
        }

        return res[lhs.length-1][rhs.length-1]
    }

    private fun cost(a: Char, b: Char ): Int = if (a == b) 0 else 1
}

/**
 * Extend the CharSequence
 */

fun CharSequence.leveinshtein(lhs: String): Int{
    if (this === lhs) return 0
    if (lhs.isEmpty()) return this.length
    if (this.isEmpty()) return lhs.length

    val res = Array(lhs.length){ IntArray(this.length)}

    for (i in 0 until lhs.length)
        res[i][0] = i

    for (j in 0 until this.length)
        res[0][j] = j

    var subst = 0
    for (j in 1 until this.length) {
        for (i in 1 until lhs.length) {
            subst = if (lhs[i] == this[j]) 0 else 1

            val deletion = res[i-1][j] + 1
            val insertion = res[i][j-1] +1
            val substitution = res[i-1][j-1] + subst
            res[i][j] = Math.min(Math.min(deletion, insertion), substitution)
        }
    }

    return res[lhs.length-1][this.length-1]
}

fun main(args: Array<String>) {
    val lev = Leveinshtein()

    var time = measureNanoTime {
        println(lev.leveinshteinDistance("rosettacode", "raisethysword"))
    }

    println("time: $time")

    time = measureNanoTime {
       println("rosettacode".leveinshtein("raisethysword"))
    }
    println("time: $time")
}