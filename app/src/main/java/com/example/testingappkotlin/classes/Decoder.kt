package com.example.testingappkotlin.classes

import android.util.Log
import kotlin.math.pow

class Decoder {

    companion object {
        fun decode(temp: String): String {
            val invalid = "Invalid Code"

            // create the same initial
            // string as in encode class
            val ini = "11111111"
            var flag = true

            // run a loop of size 8
            for (i in 0..7) {
                // check if the initial value is same
                if (ini[i] != temp[i]) {
                    flag = false
                    break
                }
            }
            var value = ""

            // reverse the encrypted code
            for (i in 8 until temp.length) {
                val ch = temp[i]
                value += ch.toString()
            }

            // create a 2 dimensional array
            val arr = Array(11101) {
                IntArray(
                    8
                )
            }
            var ind1 = -1
            var ind2 = 0

            // run a loop of size of the encrypted code
            for (i in value.indices) {

                // check if the position of the
                // string if divisible by 7
                if (i % 7 == 0) {
                    // start the value in other
                    // column of the 2D array
                    ind1++
                    ind2 = 0
                    val ch = value[i]
                    arr[ind1][ind2] = ch.code - '0'.code
                    ind2++
                } else {
                    // otherwise store the value
                    // in the same column
                    val ch = value[i]
                    arr[ind1][ind2] = ch.code - '0'.code
                    ind2++
                }
            }
            // create an array
            val num = IntArray(11111)
            var nind = 0
            var tem = 0
            var cu = 0

            // run a loop of size of the column
            for (i in 0..ind1) {
                tem = 0
                // convert binary to decimal and add them
                // from each column and store in the array
                for ((cu, j) in (6 downTo 0).withIndex()) {
                    val tem1 = 2.0.pow(cu.toDouble()).toInt()
                    tem += arr[i][j] * tem1
                }
                num[nind++] = tem
            }
            var ret = ""
            var ch: Char
            // convert the decimal ascii number to its
            // char value and add them to form a decrypted
            // string using conception function
            for (i in 0 until nind) {
                ch = num[i].toChar()
                ret += ch.toString()
            }
            Log.e("dec", "text 11 - $ret")

            // check if the encrypted code was
            // generated for this algorithm
            return if (value.length % 7 == 0 && flag) {
                // return the decrypted code
                ret
            } else {
                // otherwise return an invalid message
                invalid
            }
        }
    }

}