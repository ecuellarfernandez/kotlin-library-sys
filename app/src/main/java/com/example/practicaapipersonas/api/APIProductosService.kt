package com.example.practicaapipersonas.api

import com.example.practicaapipersonas.models.Libro
import com.example.practicaapipersonas.models.Genero
import com.example.practicaapipersonas.models.LibroData
import com.example.practicaapipersonas.models.LibroGenero
import retrofit2.Call
import retrofit2.http.*

interface APILibreriaService {
    @GET("libros")
    fun getLibros(): Call<List<Libro>>

    @GET("libros/{id}")
    fun getLibroById(@Path("id") id: Int): Call<Libro>

    @POST("libros")
    fun insertLibro(@Body libroData: LibroData): Call<Libro>

    @PUT("libros/{id}")
    fun updateLibro(@Body libroData: LibroData, @Path("id") id: Int): Call<Libro>

    @DELETE("libros/{id}")
    fun deleteLibro(@Path("id") id: Int): Call<Void>

    @GET("generos")
    fun getGeneros(): Call<List<Genero>>

    @GET("generos/{id}")
    fun getGeneroById(@Path("id") id: Int): Call<Genero>

    @POST("generos")
    fun insertGenero(@Body genero: Genero): Call<Genero>

    @PUT("generos/{id}")
    fun updateGenero(@Body genero: Genero, @Path("id") id: Int): Call<Genero>

    @DELETE("generos/{id}")
    fun deleteGenero(@Path("id") id: Int): Call<Void>

    @HTTP(method = "POST", path = "libro-generos", hasBody = true)
    fun addGeneroToLibro(@Body libroGenero: LibroGenero): Call<Void>

    @HTTP(method = "DELETE", path = "libro-generos", hasBody = true)
    fun removeGeneroFromLibro(@Body libroGenero: LibroGenero): Call<Void>
}