package com.example.practicaapipersonas.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.practicaapipersonas.databinding.ActivityLibroDetailBinding
import com.example.practicaapipersonas.models.Libro
import com.example.practicaapipersonas.repositories.LibroRepository
import com.example.practicaapipersonas.ui.viewmodels.MainViewModel

class LibroDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLibroDetailBinding
    private lateinit var libro: Libro
    //modelo
    private val model = MainViewModel()

    companion object{
        const val EDIT_LIBRO_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibroDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        libro = intent.getSerializableExtra("libro") as Libro

        binding.lblNombreLibro.text = libro.nombre
        binding.lblAutorLibro.text = libro.autor
        binding.lblEditorialLibro.text = libro.editorial
        binding.lblSinopsisLibro.text = libro.sinopsis
        binding.lblIsbnLibro.text = libro.isbn
        val calificaciontxt = libro.calificacion.toString().toFloat().toInt()
        binding.lblCalificacionLibro.text = "${calificaciontxt}/10"
        Glide.with(this)
            .load(libro.imagen)
            .into(binding.imgLibroDetail)

        binding.btnEditarLibro.setOnClickListener {
            val intent = Intent(this, EditLibroActivity::class.java)
            intent.putExtra("libro", libro)
            startActivityForResult(intent, EDIT_LIBRO_REQUEST_CODE)
        }

        binding.btnEliminarLibro.setOnClickListener {
            LibroRepository.deleteLibro(libro.id!!,
                success = {
                    model.fetchListaLibros()
                    finish()
                },
                failure = {
                    it.printStackTrace()
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == EDIT_LIBRO_REQUEST_CODE && resultCode == RESULT_OK){
            libro = data?.getSerializableExtra("libro") as Libro
            binding.lblNombreLibro.text = libro.nombre
            binding.lblAutorLibro.text = libro.autor
            binding.lblEditorialLibro.text = libro.editorial
            binding.lblSinopsisLibro.text = libro.sinopsis
            binding.lblIsbnLibro.text = libro.isbn
            val calificaciontxt = libro.calificacion.toString().toFloat().toInt()
            binding.lblCalificacionLibro.text = "${calificaciontxt}/10"
            Glide.with(this)
                .load(libro.imagen)
                .into(binding.imgLibroDetail)
        }
    }
}