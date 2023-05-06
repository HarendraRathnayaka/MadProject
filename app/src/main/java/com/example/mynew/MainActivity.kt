package com.example.mynew

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynew.activities.CompanyPage

class MainActivity : AppCompatActivity(), MyAdapter.MyClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Userdata>
    private lateinit var adapter: MyAdapter

    lateinit var imgId: Array<Int>
    lateinit var nameList: Array<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.setHasFixedSize(true)

        imgId = arrayOf(
            R.drawable.jobimages,
            R.drawable.events,
            R.drawable.companypng,
            R.drawable.profile
        )

        nameList = arrayOf(
            "Jobs",
            "Events",
            "Company",
            "My profile"
        )

        newArrayList = arrayListOf()
        getUser()

        adapter = MyAdapter(newArrayList, this)
        recyclerView.adapter = adapter
    }

    private fun getUser() {
        for (i in imgId.indices){
            val data = Userdata(imgId[i], nameList[i])
            newArrayList.add(data)
        }
    }

    override fun onClick(position: Int){
        when(position){
            0 -> startActivity(Intent(this,Jobspage::class.java))
            1 -> startActivity(Intent(this,eventspage::class.java))
            2 -> startActivity(Intent(this, CompanyPage::class.java))
            3 -> startActivity(Intent(this,myprofilepage::class.java))
        }
    }
}