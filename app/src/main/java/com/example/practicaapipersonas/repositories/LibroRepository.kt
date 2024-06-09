package com.example.practicaapipersonas.repositories

import com.example.practicaapipersonas.api.APILibreriaService
import com.example.practicaapipersonas.models.Libro
import com.example.practicaapipersonas.models.LibroData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LibroRepository {
    private val service: APILibreriaService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://apilibreria.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(APILibreriaService::class.java)
    }

    fun getLibroById(id: Int, success: (Libro) -> Unit, failure: (Throwable) -> Unit) {
        service.getLibroById(id).enqueue(object : Callback<Libro> {
            override fun onResponse(call: Call<Libro>, response: Response<Libro>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    failure(Throwable("Error ${response.code()} occurred"))
                }
            }

            override fun onFailure(call: Call<Libro>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertLibro(libro: Libro, success: (Libro) -> Unit, failure: (Throwable) -> Unit) {
        val libroData = LibroData(
            nombre = libro.nombre,
            autor = libro.autor,
            editorial = libro.editorial,
            imagen = libro.imagen,
            sinopsis = libro.sinopsis,
            isbn = libro.isbn,
            calificacion = libro.calificacion.toString()
        )
        service.insertLibro(libroData).enqueue(object : Callback<Libro> {
            override fun onResponse(call: Call<Libro>, response: Response<Libro>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    failure(Throwable("Error ${response.code()} occurred"))
                }
            }

            override fun onFailure(call: Call<Libro>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateLibro(libro: Libro, id: Int, success: (Libro) -> Unit, failure: (Throwable) -> Unit) {
        val libroData = LibroData(
            nombre = libro.nombre,
            autor = libro.autor,
            editorial = libro.editorial,
            imagen = libro.imagen,
            sinopsis = libro.sinopsis,
            isbn = libro.isbn,
            calificacion = libro.calificacion.toString()
        )
        service.updateLibro(libroData, id).enqueue(object : Callback<Libro> {
            override fun onResponse(call: Call<Libro>, response: Response<Libro>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    failure(Throwable("Error ${response.code()} occurred"))
                }
            }

            override fun onFailure(call: Call<Libro>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteLibro(id: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.deleteLibro(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Throwable("Error ${response.code()} occurred"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getLibroList(success: (List<Libro>) -> Unit, failure: (Throwable) -> Unit) {
        service.getLibros().enqueue(object : Callback<List<Libro>> {
            override fun onResponse(call: Call<List<Libro>>, response: Response<List<Libro>>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    failure(Throwable("Error ${response.code()} occurred"))
                }
            }

            override fun onFailure(call: Call<List<Libro>>, t: Throwable) {
                failure(t)
            }
        })
    }
}