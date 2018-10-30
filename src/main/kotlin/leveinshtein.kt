package com.mxr.example

import kotlin.system.measureNanoTime

class Leveinshtein {

    // inefficient
    fun leveinshteinDistanceRecursive(lhs: String, rhs: String): Int {

        return this.calculate(lhs, rhs)
    }

    private fun calculate (lhs: String, rhs: String) : Int {
        if (lhs.isEmpty()) return rhs.length
        if (rhs.isEmpty()) return lhs.length

        val substitute = calculate(lhs.substring(1), rhs.substring(1)) + cost(lhs[0], rhs[0])
        val insert = calculate(lhs, rhs.substring(1)) + 1
        val delete = calculate(lhs.substring(1), rhs) + 1

        return Math.min((Math.min(substitute, insert)), delete)
    }

    // efficient
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

fun main(args: Array<String>) {
    val lev = Leveinshtein()
    var time = measureNanoTime {
        println(lev.leveinshteinDistanceRecursive("rosettacode", "raisethysword"))
    }
    var res = time
    println("time: $res")


    time = measureNanoTime {
        println(lev.leveinshteinDistance("rosettacode", "raisethysword"))
    }

    res = time
    println("time: $res")

}