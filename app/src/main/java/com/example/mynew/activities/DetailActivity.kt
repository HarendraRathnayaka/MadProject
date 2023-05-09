package com.example.mynew.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mynew.R
import com.example.mynew.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    var imageUrl = ""
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle != null) {
            binding.locationTitle.text = bundle.getString("locationTitle")
            binding.locationDesc.text = bundle.getString("locationDesc")
            binding.locationPriority.text = bundle.getString("locationPriority")
            imageUrl = bundle.getString("Image")!!
            Glide.with(this).load(bundle.getString("Image"))
                .into(binding.detailImage)
        }
    }
}