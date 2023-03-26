package com.example.algorytmika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.Console
import kotlin.concurrent.timer
import kotlin.system.measureTimeMillis
import java.util.*
import kotlin.math.min
import java.security.MessageDigest
import java.math.BigInteger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button2).setOnClickListener {
            var dataNum = findViewById<EditText>(R.id.editTextNumber2).text.toString()
            var laps = findViewById<EditText>(R.id.editTextNumber).text.toString()
            if(dataNum!=""&&laps!="") {
                var num = dataNum.toInt()
                var txt = ""
                for (i in 0..num) {
                    txt = txt + (65 + ((0..10).random() % 3)).toChar().toString()
                }
                var pat = ""
                for (i in 0..2) {
                    pat = pat + (65 + ((0..10).random() % 3)).toChar().toString()
                }

                val executionTime1 = measureTimeMillis {
                    for (i in 0..laps.toInt()){
                    bruteForce(txt, pat)
                    }
                }
                val executionTime2 = measureTimeMillis {
                    for (i in 0..laps.toInt()) {
                        KMP(txt, pat)
                    }
                }
                val executionTime3 = measureTimeMillis {
                    for (i in 0..laps.toInt()) {
                        BoyerMoore(txt, pat)
                    }
                }
                val executionTime4 = measureTimeMillis {
                    for (i in 0.. laps.toInt()) {
                        RK(txt, pat)
                    }
                }
                findViewById<TextView>(R.id.czas1).text = executionTime1.toString()
                findViewById<TextView>(R.id.czas2).text = executionTime2.toString()
                findViewById<TextView>(R.id.czas3).text = executionTime3.toString()
                findViewById<TextView>(R.id.czas4).text = executionTime4.toString()
            }
        }



    }

    fun bruteForce(tekst: String, pattern: String): Int {
        for (i in 0..47) {
            if (pattern == tekst.slice(i..i + 2)) {
                return i
            }
        }
        return -1
    }

    fun KMP(text: String, pattern: String): Int {
        var i = 0
        var j = 0

        if (pattern.isEmpty()) {
            return 0
        }

        if (text.length < pattern.length) {
            return -1
        }


        val longestPrefixSuffix = getFiniteAutomata(pattern)

        println(Arrays.toString(longestPrefixSuffix))

        while (i < text.length) {
            if (text[i] == pattern[j]) {
                i++
                j++
                if (j == pattern.length) {

                    return i - pattern.length
                }
            } else if (j > 0) {
                j = longestPrefixSuffix[j - 1]
            } else {
                i++
            }
        }
        return -1
    }


    fun getFiniteAutomata(pattern: String): IntArray {

        var finiteAutomata = IntArray(pattern.length)

        var j = 0

        var i = 1


        while (i < pattern.length) {
            if (pattern[i] == pattern[j]) {
                j++
                finiteAutomata[i] = j
                i++
            } else run {
                var temp = i - 1
                while (temp > 0) {
                    val prevLPS = finiteAutomata[temp]
                    if (pattern[i] == pattern[prevLPS]) {
                        finiteAutomata[i++] = prevLPS + 1
                        j = prevLPS
                        break
                    } else
                        temp = prevLPS - 1
                }
                if (temp <= 0) {
                    finiteAutomata[i++] = 0
                    j = 0
                }
            }
        }
        return finiteAutomata
    }

    fun BoyerMoore(text: String, pattern: String): Int {
        var textLength = text.length
        val patternLength = pattern.length
        if (patternLength > textLength) {
            return -1
        }
        val last = IntArray(256) { -1 }
        for (i in 0 until patternLength) {
            last[pattern[i].toInt()] = i
        }

        var i = patternLength - 1
        var j = patternLength - 1
        while (i < textLength) {
            if (text[i] == pattern[j]) {
                if (j == 0) {
                    return i
                }
                i--
                j--
            } else {
                i += patternLength - Math.min(j, 1 + last[text[i].toInt()])
                j = patternLength - 1
            }
        }
        return -1
    }

    fun RK(text: String, pattern: String):Int{
        val hashPat = pattern.hashCode()
        var hastText:Int
        hastText = text.substring(0,pattern.length).hashCode()
        for(i in 0..text.length-pattern.length){
            hastText = text.substring(i,i+pattern.length).hashCode()
            if(hastText==hashPat){
                if(text.substring(i, i+pattern.length)==pattern){
                    return i
                }
            }
        }

        return -1
    }


}










