package com.example.mynew.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.mynew.R
import com.example.mynew.databinding.ActivityAddjobsBinding
import com.example.mynew.models.DataClass
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.DateFormat
import java.util.*

class addjobs : AppCompatActivity() {

    private lateinit var binding: ActivityAddjobsBinding
    var imageURL: String? = null
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddjobsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data!!.data
                binding.uploadImage.setImageURI(uri)

            } else {

                Toast.makeText(this@addjobs, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }

        binding.uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
        binding.saveButton.setOnClickListener {
            saveData()
        }
    }
    private fun saveData(){
        val storageReference = FirebaseStorage.getInstance().reference.child("Task Images")
            .child(uri!!.lastPathSegment!!)
        val builder = AlertDialog.Builder(this@addjobs)

        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage = uriTask.result
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }.addOnFailureListener {
            dialog.dismiss()
        }
    }
    private fun uploadData(){
        val title = binding.uploadTitle.text.toString()
        val desc = binding.uploadDesc.text.toString()
        val priority = binding.uploadPriority.text.toString()
        val dataClass = DataClass(title, desc, priority, imageURL)
        val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)

        FirebaseDatabase.getInstance().getReference("Jobs").child(currentDate)
            .setValue(dataClass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@addjobs, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@addjobs, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}


