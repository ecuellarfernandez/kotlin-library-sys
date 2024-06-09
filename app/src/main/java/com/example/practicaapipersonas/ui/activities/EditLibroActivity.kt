package com.example.practicaapipersonas.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practicaapipersonas.databinding.ActivityEditLibroBinding
import com.example.practicaapipersonas.models.Libro
import com.example.practicaapipersonas.repositories.LibroRepository
import com.example.practicaapipersonas.ui.viewmodels.MainViewModel

class EditLibroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditLibroBinding
    private var libro: Libro? = null
    private val model: MainViewModel by viewModels()

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
        }

        binding.btnGuardarLibro.setOnClickListener {
            val nombre = binding.edtNombreLibro.text.toString()
            val autor = binding.edtAutorLibro.text.toString()
            val editorial = binding.edtEditorialLibro.text.toString()
            val sinopsis = binding.edtSinopsisLibro.text.toString()
            val imagen = binding.edtImagenLibro.text.toString()
            val isbn = binding.edtIsbnLibro.text.toString()
            val calificacion = binding.edtCalificacionLibro.text.toString().toFloat()
            val generos = libro?.generos ?: emptyList()

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
                libro?.generos = libro?.generos ?: emptyList()
                LibroRepository.updateLibro(libro!!, id = libro!!.id!!,
                    success = {
                        model.fetchListaLibros()
                        val intent = Intent().apply{
                            putExtra("libro", libro)
                        }
                        setResult(RESULT_OK, intent)
                        finish()
                    },
                    failure = { throwable ->
                        throwable.printStackTrace()
                        Toast.makeText(this, "Error ACTUALIZANDO libro", Toast.LENGTH_SHORT).show()
                    })
            } else {
                val newLibro = Libro(nombre, autor, editorial, imagen, sinopsis, isbn, calificacion, generos)
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
}