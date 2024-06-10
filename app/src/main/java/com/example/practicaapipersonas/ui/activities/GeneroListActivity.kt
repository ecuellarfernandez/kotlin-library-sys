package com.example.practicaapipersonas.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaapipersonas.databinding.ActivityGeneroListBinding
import com.example.practicaapipersonas.models.Genero
import com.example.practicaapipersonas.repositories.GeneroRepository
import com.example.practicaapipersonas.ui.adapters.GeneroAdapter
import com.example.practicaapipersonas.ui.viewmodels.GeneroViewModel

class GeneroListActivity : AppCompatActivity(), GeneroAdapter.OnGeneroClickListener {
    private lateinit var binding: ActivityGeneroListBinding
    private lateinit var generoAdapter: GeneroAdapter
    private val generoViewModel: GeneroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneroListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize generoAdapter before calling setupRecyclerView
        generoAdapter = GeneroAdapter(mutableListOf(), this)

        setupRecyclerView()
        setupViewModel()
    }
    private fun setupRecyclerView() {
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

    override fun onGeneroClick(genero: Genero) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("generoId", genero.id)
        startActivity(intent)
    }


    override fun onGeneroEdit(genero: Genero) {
        val intent = Intent(this, EditGeneroActivity::class.java)
        intent.putExtra("genero", genero)
        startActivityForResult(intent, 1)
    }

    override fun onGeneroDelete(genero: Genero) {
        GeneroRepository.deleteGenero(genero.id!!, {
            generoViewModel.fetchListaGeneros()
        }, {
            it.printStackTrace()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            generoViewModel.fetchListaGeneros()
        }
    }
}