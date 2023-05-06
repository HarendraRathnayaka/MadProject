package com.example.mynew.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mynew.models.CompanyModel
import com.example.mynew.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddCompany : AppCompatActivity() {

    private lateinit var company_name: EditText
    private lateinit var company_industry: EditText
    private lateinit var company_vacancies: EditText
    private lateinit var company_no: EditText
    private lateinit var company_mail: EditText
    private lateinit var company_address: EditText
    private lateinit var company_link: EditText
    private lateinit var company_description: EditText
    private lateinit var btnsavecompany: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)

        company_name = findViewById(R.id.company_name)
        company_industry = findViewById(R.id.company_industry)
        company_vacancies = findViewById(R.id.company_vacancies)
        company_no = findViewById(R.id.company_no)
        company_mail = findViewById(R.id.company_mail)
        company_address = findViewById(R.id.company_address)
        company_link = findViewById(R.id.company_link)
        company_description = findViewById(R.id.company_description)
        btnsavecompany = findViewById(R.id.btnsavecompany)

        dbRef = FirebaseDatabase.getInstance().getReference("Companies")

        btnsavecompany.setOnClickListener {

            if (!validate()) {
                return@setOnClickListener
            }
            saveCompanyData()
            val intent = Intent(this, CompanyPage::class.java)
            startActivity(intent)
        }

    }

    private fun saveCompanyData() {

        //Getting values
        val companyName = company_name.text.toString()
        val companyIndustry = company_industry.text.toString()
        val companyVacancies = company_vacancies.text.toString()
        val companyNo = company_no.text.toString()
        val companyMail = company_mail.text.toString()
        val companyAddress = company_address.text.toString()
        val companyLink = company_link.text.toString()
        val companyDescription = company_description.text.toString()


        val companyId = dbRef.push().key!!

        val company = CompanyModel(
            companyId,
            companyName,
            companyIndustry,
            companyVacancies,
            companyNo,
            companyMail,
            companyAddress,
            companyLink,
            companyDescription
        )

        dbRef.child(companyId).setValue(company)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                //clear fields after save
                company_name.text.clear()
                company_industry.text.clear()
                company_vacancies.text.clear()
                company_no.text.clear()
                company_mail.text.clear()
                company_address.text.clear()
                company_link.text.clear()
                company_description.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun validate(): Boolean {

        //Getting values
        val companyName = company_name.text.toString()
        val companyIndustry = company_industry.text.toString()
        val companyVacancies = company_vacancies.text.toString()
        val companyNo = company_no.text.toString()
        val companyMail = company_mail.text.toString()
        val companyAddress = company_address.text.toString()

        //form validation
        if (companyName.isEmpty()) {
            company_name.error = "Please fill this area"
            return false
        }
        if (companyIndustry.isEmpty()) {
            company_industry.error = "Please fill this area"
            return false
        }
        if (companyVacancies.isEmpty()) {
            company_vacancies.error = "Please fill this area"
            return false
        }
        if (companyNo.isEmpty()) {
            company_no.error = "Please fill this area"
            return false
        }
        if (companyMail.isEmpty()) {
            company_mail.error = "Please fill this area"
            return false
        }
        if (companyAddress.isEmpty()) {
            company_address.error = "Please fill this area"
            return false
        }
        if (!companyNo.matches("0\\d{9}".toRegex())) {
            company_no.error = "Please enter a 10-digit number starting with 0"
            return false
        }
        if (!companyMail.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex())) {
            company_mail.error = "Please enter a valid email address Eg: abc@mail.com"
            return false
        }

        return true

    }
}