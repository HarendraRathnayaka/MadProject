package com.example.mynew.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mynew.R
import com.example.mynew.adapters.JobTipsAdapter
import com.example.mynew.databinding.ActivityJobTipsBinding
import com.example.mynew.models.JobTipsModel
import com.google.firebase.database.*
import java.util.*

class JobTipsActivity : AppCompatActivity() {
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dataList: ArrayList<JobTipsModel>
    private lateinit var adapter: JobTipsAdapter
    private lateinit var binding: ActivityJobTipsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val gridLayoutManager = GridLayoutManager(this@JobTipsActivity, 1)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.search.clearFocus()

        val builder = AlertDialog.Builder(this@JobTipsActivity)
        builder.setCancelable(false)
   //     builder.setView(com.google.firebase.database.R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        adapter = JobTipsAdapter(this@JobTipsActivity, dataList)
        binding.recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("JobTips")
        dialog.show()
        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(JobTipsModel::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }


        })
        binding.fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@JobTipsActivity, UploadJobTipsActivity::class.java)
            startActivity(intent)
        })


        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })
    }
    fun searchList(text: String) {
        val searchList = java.util.ArrayList<JobTipsModel>()
        for (JobTipsModel in dataList) {
            if (JobTipsModel.JobTitle?.lowercase()
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(JobTipsModel)
            }
        }
        adapter.searchDataList(searchList)
    }


}