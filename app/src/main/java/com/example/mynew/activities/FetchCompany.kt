package com.example.mynew.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynew.R
import com.example.mynew.adapters.CompanyAdapter
import com.example.mynew.databinding.ActivityFetchCompanyBinding
import com.example.mynew.models.CompanyModel
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class FetchCompany : AppCompatActivity() {

    private lateinit var compRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var companyList: ArrayList<CompanyModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var adapter: CompanyAdapter
    private lateinit var binding: ActivityFetchCompanyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compRecyclerView = findViewById(R.id.rvCompany)
        compRecyclerView.layoutManager = LinearLayoutManager(this)
        compRecyclerView.setHasFixedSize(true)

        tvLoadingData = findViewById(R.id.tvLoadingData)

        companyList = arrayListOf<CompanyModel>()

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })

        getCompaniesData()
    }

    private fun getCompaniesData() {
        compRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Companies")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                companyList.clear()
                if (snapshot.exists()) {
                    for (companySnap in snapshot.children) {
                        val companyData = companySnap.getValue(CompanyModel::class.java)
                        companyList.add(companyData!!)
                    }
                    adapter = CompanyAdapter(companyList)
                    compRecyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object : CompanyAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchCompany, CompanyDetails::class.java)

                            //put extras
                            intent.putExtra("companyId", companyList[position].companyId)
                            intent.putExtra("companyName", companyList[position].companyName)
                            intent.putExtra("companyIndustry", companyList[position].companyIndustry)
                            intent.putExtra("companyVacancies", companyList[position].companyVacancies)
                            intent.putExtra("companyNo", companyList[position].companyNo)
                            intent.putExtra("companyMail", companyList[position].companyMail)
                            intent.putExtra("companyAddress", companyList[position].companyAddress)
                            intent.putExtra("companyLink", companyList[position].companyLink)
                            intent.putExtra("companyDescription", companyList[position].companyDescription)
                            startActivity(intent)
                        }
                    })

                    compRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun searchList(text: String) {
        val searchList = java.util.ArrayList<CompanyModel>()
        for (DataClass in companyList) {
            if (DataClass.companyName?.lowercase()
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(DataClass)
            }
        }
        adapter.searchDataList(searchList)
    }
}
