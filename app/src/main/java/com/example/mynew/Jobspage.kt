package com.example.mynew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Jobspage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobspage)

        val textView: TextView = findViewById(R.id.textView5)
    }
}