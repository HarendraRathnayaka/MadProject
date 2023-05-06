package com.example.mynew.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mynew.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CompanyPage : AppCompatActivity() {

    private lateinit var btnaddcompany: Button
    private lateinit var btnfetchcompany: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_companypage)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnaddcompany = findViewById(R.id.btnaddcompany)
        btnfetchcompany = findViewById(R.id.btnfetchcompany)

        btnaddcompany.setOnClickListener {
            val intent = Intent(this, AddCompany::class.java)
            startActivity(intent)
        }

        btnfetchcompany.setOnClickListener {
            val intent = Intent(this, FetchCompany::class.java)
            startActivity(intent)
        }
    }
}