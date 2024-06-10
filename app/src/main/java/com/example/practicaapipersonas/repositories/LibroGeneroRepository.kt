package com.example.practicaapipersonas.repositories

import com.example.practicaapipersonas.api.APILibreriaService
import com.example.practicaapipersonas.models.LibroGenero
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

fun addGeneroToLibro(libroGenero: LibroGenero, success: () -> Unit, failure: (Throwable) -> Unit) {
    service.addGeneroToLibro(libroGenero).enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                println("Response: $response")
                success()
            } else {
                val errorBody = response.errorBody()?.string()
                println("Error response: $response")
                failure(Throwable("Error ${response.code()} occurred while adding genre to book. Error Body: $errorBody"))
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            println("Failure: $t")
            failure(t)
        }
    })
}

fun removeGeneroFromLibro(libroGenero: LibroGenero, success: () -> Unit, failure: (Throwable) -> Unit) {
    service.removeGeneroFromLibro(libroGenero).enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                success()
            } else {
                val errorBody = response.errorBody()?.string()
                failure(Throwable("Error ${response.code()} occurred while removing genre from book. Error Body: $errorBody"))
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            failure(t)
        }
    })
}
}