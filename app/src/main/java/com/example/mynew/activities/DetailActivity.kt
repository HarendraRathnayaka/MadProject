package com.example.mynew.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mynew.databinding.ActivityDetailBinding
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var deleteButton: FloatingActionButton
    private lateinit var editButton: FloatingActionButton
    private var imageURL: String? = null
    private var key: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deleteButton = binding.deleteButton
        editButton = binding.editButton

        val bundle = intent.extras
        if (bundle != null) {
            binding.detailDesc.text = bundle.getString("Description")
            binding.detailTitle.text = bundle.getString("Title")
            binding.detailPriority.text = bundle.getString("Priority")
            key = bundle.getString("Key")

            val image = bundle.getString("Image")
            if (!image.isNullOrEmpty()) {
                imageURL = image
                Glide.with(this).load(imageURL).into(binding.detailImage)
            }

        }
        deleteButton.setOnClickListener {
            key?.let {
                val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Jobs")
                val storage: FirebaseStorage = FirebaseStorage.getInstance()
                val storageReference: StorageReference = storage.getReferenceFromUrl(imageURL!!)
                storageReference.delete().addOnSuccessListener {
                    reference.child(key!!).removeValue().addOnSuccessListener {
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext, Jobspage::class.java))
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed to delete data from Firebase", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to delete image from Firebase Storage", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}