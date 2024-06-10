package com.example.practicaapipersonas.ui.activities

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practicaapipersonas.databinding.ActivityEditGeneroBinding
import com.example.practicaapipersonas.models.Genero
import com.example.practicaapipersonas.repositories.GeneroRepository
import com.example.practicaapipersonas.ui.viewmodels.GeneroViewModel

class EditGeneroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditGeneroBinding
    private var genero: Genero? = null
    //modelo
    private val model: GeneroViewModel by viewModels()
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
                model.fetchListaGeneros()
                return@setOnClickListener
            }

            val newGenero = Genero(name)
            if (genero == null) {
                GeneroRepository.insertGenero(newGenero,
                    success = {
                        Toast.makeText(this, "Género insertado con éxito", Toast.LENGTH_SHORT).show()
                        model.fetchListaGeneros()
                        setResult(Activity.RESULT_OK)
                        finish()
                    },
                    failure = { exception ->
                        Toast.makeText(this, "Error al insertar el género: ${exception.message}", Toast.LENGTH_SHORT).show()
                    })
            } else {
                genero?.id?.let { id ->
                    GeneroRepository.updateGenero(newGenero, id,
                        success = {
                            Toast.makeText(this, "Género actualizado con éxito", Toast.LENGTH_SHORT).show()
                            model.fetchListaGeneros()
                            setResult(Activity.RESULT_OK)
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