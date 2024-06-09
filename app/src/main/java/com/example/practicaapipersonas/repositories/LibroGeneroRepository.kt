package com.example.practicaapipersonas.repositories

import com.example.practicaapipersonas.api.APILibreriaService
import com.example.practicaapipersonas.models.LibroGenero
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LibroGeneroRepository {
    private val service: APILibreriaService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://apilibreria.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(APILibreriaService::class.java)
    }

    fun addGeneroToLibro(libroGenero: LibroGenero) = service.addGeneroToLibro(libroGenero)
    fun removeGeneroFromLibro(libroGenero: LibroGenero) = service.removeGeneroFromLibro(libroGenero)
}