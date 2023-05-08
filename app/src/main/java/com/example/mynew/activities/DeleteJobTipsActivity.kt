package com.example.mynew.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mynew.R
import com.example.mynew.databinding.ActivityDeleteJobTipsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteJobTipsActivity : AppCompatActivity() {
  //  private lateinit var navController: NavController

    private lateinit var binding: ActivityDeleteJobTipsBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteJobTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.deleteButton.setOnClickListener {
            val jobTips = binding.deleteJobTips.text.toString()
            if (jobTips.isNotEmpty())
                deleteData(jobTips)
            else
                Toast.makeText(this, "Please enter Location", Toast.LENGTH_SHORT).show()
        }
    }
    private fun deleteData(locationId: String){
        database = FirebaseDatabase.getInstance().getReference("JobTips")
        database.child(locationId).removeValue().addOnSuccessListener {
            binding.deleteJobTips.text.clear()
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            finish()

        }.addOnFailureListener {
            Toast.makeText(this, "Unable to delete", Toast.LENGTH_SHORT).show()
        }
    }

    //to implement correct backward navigation
   // override fun onSupportNavigateUp(): Boolean {
    //    return navController.navigateUp() || super.onSupportNavigateUp()
   // }

}