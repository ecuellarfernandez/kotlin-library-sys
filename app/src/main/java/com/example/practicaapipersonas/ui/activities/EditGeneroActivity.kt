package com.example.practicaapipersonas.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practicaapipersonas.databinding.ActivityEditGeneroBinding

class EditGeneroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditGeneroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGeneroBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}