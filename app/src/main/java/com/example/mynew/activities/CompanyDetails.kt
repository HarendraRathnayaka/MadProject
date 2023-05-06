package com.example.mynew.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mynew.R
import com.example.mynew.models.CompanyModel
import com.google.firebase.database.FirebaseDatabase

class CompanyDetails : AppCompatActivity() {

    private lateinit var tvcName: TextView
    private lateinit var tvcIndustry: TextView
    private lateinit var tvcVacancies: TextView
    private lateinit var tvcNo: TextView
    private lateinit var tvcMail: TextView
    private lateinit var tvcAddress: TextView
    private lateinit var tvcLink: TextView
    private lateinit var tvcDescription: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("companyId").toString() ,
                intent.getStringExtra("companyName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("companyId").toString()
            )
        }
    }

    private fun initView() {

        tvcName = findViewById(R.id.tvcName)
        tvcIndustry = findViewById(R.id.tvcIndustry)
        tvcVacancies = findViewById(R.id.tvcVacancies)
        tvcNo = findViewById(R.id.tvcNo)
        tvcMail = findViewById(R.id.tvcMail)
        tvcAddress = findViewById(R.id.tvcAddress)
        tvcLink = findViewById(R.id.tvcLink)
        tvcDescription = findViewById(R.id.tvcDescription)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {

        tvcName.text = intent.getStringExtra("companyName")
        tvcIndustry.text = intent.getStringExtra("companyIndustry")
        tvcVacancies.text = intent.getStringExtra("companyVacancies")
        tvcNo.text = intent.getStringExtra("companyNo")
        tvcMail.text = intent.getStringExtra("companyMail")
        tvcAddress.text = intent.getStringExtra("companyAddress")
        tvcLink.text = intent.getStringExtra("companyLink")
        tvcDescription.text = intent.getStringExtra("companyDescription")
    }

    private fun openUpdateDialog(companyId: String, companyName: String) {

        val cDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val cDialogView = inflater.inflate(R.layout.company_update_dialog, null)

        cDialog.setView(cDialogView)

        val etcompany_name = cDialogView.findViewById<EditText>(R.id.etcompany_name)
        val etcompany_industry = cDialogView.findViewById<EditText>(R.id.etcompany_industry)
        val etcompany_vacancies = cDialogView.findViewById<EditText>(R.id.etcompany_vacancies)
        val etcompany_no = cDialogView.findViewById<EditText>(R.id.etcompany_no)
        val etcompany_mail = cDialogView.findViewById<EditText>(R.id.etcompany_mail)
        val etcompany_address = cDialogView.findViewById<EditText>(R.id.etcompany_address)
        val etcompany_link = cDialogView.findViewById<EditText>(R.id.etcompany_link)
        val etcompany_description = cDialogView.findViewById<EditText>(R.id.etcompany_description)
        val btnupdatecompany = cDialogView.findViewById<Button>(R.id.btnupdatecompany)

        etcompany_name.setText(intent.getStringExtra("companyName").toString())
        etcompany_industry.setText(intent.getStringExtra("companyIndustry").toString())
        etcompany_vacancies.setText(intent.getStringExtra("companyVacancies").toString())
        etcompany_no.setText(intent.getStringExtra("companyNo").toString())
        etcompany_mail.setText(intent.getStringExtra("companyMail").toString())
        etcompany_address.setText(intent.getStringExtra("companyAddress").toString())
        etcompany_link.setText(intent.getStringExtra("companyLink").toString())
        etcompany_description.setText(intent.getStringExtra("companyDescription").toString())

        //cDialog.setTitle("Updating $companyName Records")

        val alertDialog = cDialog.create()
        alertDialog.show()

        btnupdatecompany.setOnClickListener {
            updateCompanyData(
                companyId,
                etcompany_name.text.toString(),
                etcompany_industry.text.toString(),
                etcompany_vacancies.text.toString(),
                etcompany_no.text.toString(),
                etcompany_mail.text.toString(),
                etcompany_address.text.toString(),
                etcompany_link.text.toString(),
                etcompany_description.text.toString()
            )

            Toast.makeText(applicationContext, "Company Data Updated", Toast.LENGTH_LONG).show()

            //Setting updated data to textViews in details page
            tvcName.text = etcompany_name.text.toString()
            tvcIndustry.text = etcompany_industry.text.toString()
            tvcVacancies.text = etcompany_vacancies.text.toString()
            tvcNo.text = etcompany_no.text.toString()
            tvcMail.text = etcompany_mail.text.toString()
            tvcAddress.text = etcompany_address.text.toString()
            tvcLink.text = etcompany_link.text.toString()
            tvcDescription.text = etcompany_description.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateCompanyData(
        id: String,
        name: String,
        industry: String,
        vacancies: String,
        no: String,
        mail: String,
        address: String,
        link: String,
        description: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Companies").child(id)
        val companyInfo = CompanyModel(id, name, industry, vacancies, no, mail, address, link, description)
        dbRef.setValue(companyInfo)
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Companies").child(id)
        val cTask = dbRef.removeValue()

        cTask.addOnSuccessListener {
            Toast.makeText(this, "Company data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchCompany::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}