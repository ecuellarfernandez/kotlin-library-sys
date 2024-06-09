package com.example.practicaapipersonas.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaapipersonas.databinding.ActivityGeneroListBinding
import com.example.practicaapipersonas.ui.adapters.GeneroAdapter
import com.example.practicaapipersonas.ui.viewmodels.GeneroViewModel

class GeneroListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGeneroListBinding
    private lateinit var generoAdapter: GeneroAdapter
    private val generoViewModel: GeneroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneroListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        generoAdapter = GeneroAdapter(mutableListOf())
        binding.generoList.apply {
            layoutManager = LinearLayoutManager(this@GeneroListActivity)
            adapter = generoAdapter
        }
    }

    private fun setupViewModel() {
        generoViewModel.generoList.observe(this) { generos ->
            generoAdapter.updateData(generos.toMutableList())
        }
        generoViewModel.fetchListaGeneros()
    }
}