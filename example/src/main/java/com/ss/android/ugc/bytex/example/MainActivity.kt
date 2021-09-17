package com.ss.android.ugc.bytex.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Handle coverage log info, send to the server

        ThreadTest.test()
        val test = ThreadTest()
        test.testThread()
        test.testTask()
        test.handlerThread()
        test.execute()
        test.testTimer()
    }

    fun hello() {
        println("Hello")
    }
}
