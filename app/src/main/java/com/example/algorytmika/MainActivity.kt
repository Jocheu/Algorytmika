package com.example.algorytmika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Console
import kotlin.concurrent.timer
import kotlin.system.measureTimeMillis
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button2).setOnClickListener {
            var txt = ""
            for(i in 0..49){
                txt = txt + (65 + ((0..10).random()%3)).toChar().toString()
            }
            var pat = ""
            for(i in 0..2){
                pat = pat + (65 + ((0..10).random()%3)).toChar().toString()
            }
            findViewById<TextView>(R.id.textView).text = txt
            findViewById<TextView>(R.id.textView2).text = pat
            findViewById<TextView>(R.id.czas1).text = bruteForce(txt, pat).toString()
            findViewById<TextView>(R.id.czas2).text = KMP(txt,pat).toString()
        }




    }

    fun bruteForce(tekst: String, pattern:String):Int{
        for(i in 0..47){
            if(pattern == tekst.slice(i..i+2)){
                return i
            }
        }
        return -1
    }

    fun KMP(text: String, pattern: String):Int{
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

