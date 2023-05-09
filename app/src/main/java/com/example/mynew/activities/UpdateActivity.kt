package com.example.mynew.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mynew.R
import com.example.mynew.models.DataClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class UpdateActivity : AppCompatActivity() {
    private lateinit var updateButton: Button
    private lateinit var updateDesc: EditText
    private lateinit var updateImage: ImageView
    private lateinit var updateLang: EditText
    private lateinit var updateTitle: EditText
    private lateinit var uri: Uri
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var key: String
    private lateinit var oldImageURL: String
    private lateinit var imageUrl: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        updateButton = findViewById(R.id.updateButton)
        updateDesc = findViewById(R.id.updateDesc)
        updateImage = findViewById(R.id.updateImage)
        updateLang = findViewById(R.id.updateLang)
        updateTitle = findViewById(R.id.updateTitle)

        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    uri = data?.data!!
                    updateImage.setImageURI(uri)
                } else {
                    Toast.makeText(this@UpdateActivity, "No Image Selected", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        val bundle = intent.extras
        if (bundle != null) {
            Glide.with(this@UpdateActivity).load(bundle.getString("Image")).into(updateImage)
            updateTitle.setText(bundle.getString("Title"))
            updateDesc.setText(bundle.getString("Description"))
            updateLang.setText(bundle.getString("Language"))
            key = bundle.getString("Key")!!
            oldImageURL = bundle.getString("Image")!!
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Jobs").child(key)

        updateImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        updateButton.setOnClickListener {
            saveData()
            val intent = Intent(this@UpdateActivity, Jobspage::class.java)
            startActivity(intent)
        }
    }

    private fun saveData() {
        storageReference =
            FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.lastPathSegment!!)
        val builder = AlertDialog.Builder(this@UpdateActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        storageReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage = uriTask.result
            imageUrl = urlImage.toString()
            updateData()
            dialog.dismiss()
        }.addOnFailureListener { e ->
            dialog.dismiss()
        }
    }

    private fun updateData() {
        title = updateTitle.text.toString().trim()
        val desc = updateDesc.text.toString().trim()
        val lang = updateLang.text.toString()
        val dataClass = DataClass(title as String, desc, lang, imageUrl)
        databaseReference.setValue(dataClass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL)
                reference.delete()
                Toast.makeText(this@UpdateActivity, "Updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this@UpdateActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}