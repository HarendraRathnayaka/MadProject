package com.example.mynew.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mynew.adapters.MyHomeAdapter
import com.example.mynew.R
import com.example.mynew.databinding.ActivityJobspageBinding
import com.example.mynew.models.DataClass
import com.google.firebase.database.*

class Jobspage : AppCompatActivity() {

    private lateinit var binding: ActivityJobspageBinding
    private lateinit var dataList: ArrayList<DataClass>
    private lateinit var adapter: MyHomeAdapter
    var databaseReference:DatabaseReference? = null
    var eventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobspageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@Jobspage, 1)
        binding.recyclerView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@Jobspage)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        adapter = MyHomeAdapter(this@Jobspage, dataList)
        binding.recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Jobs")
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    if (dataClass != null){
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.show()
            }

        })

        binding.fab.setOnClickListener {
            val intent = Intent(this@Jobspage, addjobs::class.java)
            startActivity(intent)
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText) // Call searchList() function here
                return true
            }
        })

    }
    //After OnCreate
    fun searchList(text: String){
        val searchList = ArrayList<DataClass>()
        for (dataClass in dataList){
            if (dataClass.dataTitle?.lowercase()?.contains(text.lowercase()) == true){
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }

}