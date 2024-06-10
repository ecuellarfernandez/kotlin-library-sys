package com.example.practicaapipersonas.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practicaapipersonas.databinding.ActivityEditLibroBinding
import com.example.practicaapipersonas.models.Genero
import com.example.practicaapipersonas.models.Libro
import com.example.practicaapipersonas.models.LibroGenero
import com.example.practicaapipersonas.repositories.LibroGeneroRepository
import com.example.practicaapipersonas.repositories.LibroRepository
import com.example.practicaapipersonas.ui.viewmodels.GeneroViewModel
import com.example.practicaapipersonas.ui.viewmodels.MainViewModel

class EditLibroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditLibroBinding
    private var libro: Libro? = null
    private val model: MainViewModel by viewModels()
    private val generoViewModel: GeneroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        libro = intent.getSerializableExtra("libro") as? Libro

        if (libro != null) {
            binding.edtNombreLibro.setText(libro?.nombre)
            binding.edtAutorLibro.setText(libro?.autor)
            binding.edtEditorialLibro.setText(libro?.editorial)
            binding.edtSinopsisLibro.setText(libro?.sinopsis)
            binding.edtIsbnLibro.setText(libro?.isbn)
            binding.edtCalificacionLibro.setText(libro?.calificacion.toString())
            binding.edtImagenLibro.setText(libro?.imagen)
        } else {
            binding.edtNombreLibro.setText("")
            binding.edtAutorLibro.setText("")
            binding.edtEditorialLibro.setText("")
            binding.edtSinopsisLibro.setText("")
            binding.edtIsbnLibro.setText("")
            binding.spinnerGeneros.visibility = android.view.View.GONE
            binding.btnAddGenero.visibility = android.view.View.GONE
            binding.btnDeleteGeneroEdit.visibility = android.view.View.GONE
        }

        generoViewModel.generoList.observe(this) { generos ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, generos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerGeneros.adapter = adapter
        }
        generoViewModel.fetchListaGeneros()

        binding.btnAddGenero.setOnClickListener {
            val selectedGenero = binding.spinnerGeneros.selectedItem as Genero
            val libroGenero = LibroGenero(libro!!.id!!, selectedGenero.id!!)
            LibroGeneroRepository.addGeneroToLibro(libroGenero,
                success = {
                    updateLibroAndReturn()
                },
                failure = { throwable ->
                    throwable.printStackTrace()
                })
        }

        binding.btnDeleteGeneroEdit.isEnabled = libro != null
        binding.btnDeleteGeneroEdit.setOnClickListener {
            val selectedGenero = binding.spinnerGeneros.selectedItem as Genero
            val libroGenero = LibroGenero(libro!!.id!!, selectedGenero.id!!)
            LibroGeneroRepository.removeGeneroFromLibro(libroGenero,
                success = {
                    updateLibroAndReturn()
                },
                failure = { throwable ->
                    throwable.printStackTrace()
                })
        }

        binding.btnGuardarLibro.setOnClickListener {
            val nombre = binding.edtNombreLibro.text.toString()
            val autor = binding.edtAutorLibro.text.toString()
            val editorial = binding.edtEditorialLibro.text.toString()
            val sinopsis = binding.edtSinopsisLibro.text.toString()
            val imagen = binding.edtImagenLibro.text.toString()
            val isbn = binding.edtIsbnLibro.text.toString()
            val calificacion = binding.edtCalificacionLibro.text.toString().toFloat()

            if (nombre.isEmpty() || autor.isEmpty() || editorial.isEmpty() || sinopsis.isEmpty() || imagen.isEmpty() || isbn.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos...", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (libro != null) {
                libro?.nombre = nombre
                libro?.autor = autor
                libro?.editorial = editorial
                libro?.imagen = imagen
                libro?.sinopsis = sinopsis
                libro?.isbn = isbn
                libro?.calificacion = calificacion

                updateLibroAndReturn()
            } else {
                val newLibro = Libro(nombre, autor, editorial, imagen, sinopsis, isbn, calificacion, emptyList())
                LibroRepository.insertLibro(newLibro,
                    success = {
                        model.fetchListaLibros()
                        finish()
                    },
                    failure = { throwable ->
                        throwable.printStackTrace()
                        Toast.makeText(this, "Error INSERTANDO libro", Toast.LENGTH_SHORT).show()
                    })
            }
        }
    }

    private fun updateLibroAndReturn() {
        LibroRepository.updateLibro(libro!!, id = libro!!.id!!,
            success = {
                LibroRepository.getLibroById(libro!!.id!!,
                    success = { updatedLibro ->
                        model.fetchListaLibros()
                        val intent = Intent().apply{
                            putExtra("libro", updatedLibro)
                        }
                        setResult(RESULT_OK, intent)
                        finish()
                    },
                    failure = { throwable ->
                        throwable.printStackTrace()
                        Toast.makeText(this, "Error OBTENIENDO libro actualizado", Toast.LENGTH_SHORT).show()
                    })
            },
            failure = { throwable ->
                throwable.printStackTrace()
                Toast.makeText(this, "Error ACTUALIZANDO libro", Toast.LENGTH_SHORT).show()
            })
    }
}