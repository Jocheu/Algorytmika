package com.example.algorytmika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Console
import kotlin.concurrent.timer
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        bruteForce(txt, pat)



    }

    fun bruteForce(tekst: String, pattern:String){
        var occurances = 0
        for(i in 0..47){
            if(pattern == tekst.slice(i..i+2)){
                findViewById<TextView>(R.id.czas2).text =  "udało sie"
            }
        }
    }
}