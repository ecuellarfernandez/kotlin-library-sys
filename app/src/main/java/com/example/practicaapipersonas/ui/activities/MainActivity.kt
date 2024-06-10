package com.example.practicaapipersonas.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaapipersonas.R
import com.example.practicaapipersonas.databinding.ActivityMainBinding
import com.example.practicaapipersonas.models.Genero
import com.example.practicaapipersonas.models.Libro
import com.example.practicaapipersonas.repositories.LibroRepository
import com.example.practicaapipersonas.ui.adapters.LibroAdapter
import com.example.practicaapipersonas.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), LibroAdapter.OnLibroClickListener {
    lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    private var selectedLibro: Libro? = null // Variable para mantener el libro seleccionado
    private var generoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generoId = intent.getIntExtra("generoId", -1)
        if (generoId != -1) {
            title = "Libros del género $generoId"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEventListeners()
        setupRecyclerView()
        setupViewModelListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_go_next_page) {
            selectedLibro?.let { libro ->
                val intent = Intent(this, LibroDetailActivity::class.java)
                intent.putExtra("libro", libro)
                startActivity(intent)
            }
        }else if (item.itemId == R.id.action_categorias) {
            val intent = Intent(this, GeneroListActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        model.fetchListaLibros()
    }
    private fun setupEventListeners() {
        binding.fabAddLibro.setOnClickListener {
            val intent = Intent(this, EditLibroActivity::class.java)
            startActivity(intent)
        }
        binding.fabAddCategoria.setOnClickListener {
            val intent = Intent(this, EditGeneroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModelListeners() {
        model.libroList.observe(this) { libros ->
            val filteredLibros = if (generoId != -1) {
                libros.filter { libro -> libro.generos.any { genero -> genero.id == generoId } }
            } else {
                libros
            }
            val ordenarLibrosCalificacion = filteredLibros.sortedByDescending { libro -> libro.calificacion }
            val adapter = (binding.lstPersonas.adapter as LibroAdapter)
            adapter.updateData(ordenarLibrosCalificacion)
        }
    }

    private fun setupRecyclerView() {
        binding.lstPersonas.apply {
            this.adapter = LibroAdapter(mutableListOf(), this@MainActivity)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onLibroClick(libro: Libro) {
        selectedLibro = libro
        val intent = Intent(this, LibroDetailActivity::class.java)
        intent.putExtra("libro", libro)
        startActivity(intent)
    }

    override fun onLibroDelete(libro: Libro) {
        LibroRepository.deleteLibro(libro.id!!,
            success = {
                model.fetchListaLibros()
            },
            failure = {
                it.printStackTrace()
            })
    }

    override fun onLibroEdit(libro: Libro) {
        val intent = Intent(this, EditLibroActivity::class.java)
        intent.putExtra("libro", libro)
        startActivity(intent)
    }
}