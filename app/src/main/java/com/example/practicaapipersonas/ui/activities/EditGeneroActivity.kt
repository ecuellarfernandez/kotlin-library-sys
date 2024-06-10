package com.example.practicaapipersonas.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practicaapipersonas.databinding.ActivityEditGeneroBinding
import com.example.practicaapipersonas.models.Genero
import com.example.practicaapipersonas.repositories.GeneroRepository

class EditGeneroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditGeneroBinding
    private var genero: Genero? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGeneroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        genero = intent.getSerializableExtra("genero") as? Genero

        genero?.let {
            binding.edtNombreGenero.setText(it.nombre)
        }

        binding.btnGuardarGenero.setOnClickListener {
            val name = binding.edtNombreGenero.text.toString()
            if (name.isBlank()) {
                Toast.makeText(this, "El nombre del género no puede estar vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newGenero = Genero(name)
            if (genero == null) {
                // Insert new genre
                GeneroRepository.insertGenero(newGenero,
                    success = {
                        Toast.makeText(this, "Género insertado con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    failure = { exception ->
                        Toast.makeText(this, "Error al insertar el género: ${exception.message}", Toast.LENGTH_SHORT).show()
                    })
            } else {
                // Update existing genre
                genero?.id?.let { id ->
                    GeneroRepository.updateGenero(newGenero, id,
                        success = {
                            Toast.makeText(this, "Género actualizado con éxito", Toast.LENGTH_SHORT).show()
                            finish()
                        },
                        failure = { exception ->
                            Toast.makeText(this, "Error al actualizar el género: ${exception.message}", Toast.LENGTH_SHORT).show()
                        })
                }
            }
        }
    }
}