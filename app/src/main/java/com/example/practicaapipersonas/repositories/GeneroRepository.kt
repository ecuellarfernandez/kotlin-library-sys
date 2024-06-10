package com.example.practicaapipersonas.repositories

import com.example.practicaapipersonas.api.APILibreriaService
import com.example.practicaapipersonas.models.Genero
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeneroRepository {
    private val service: APILibreriaService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://apilibreria.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(APILibreriaService::class.java)
    }

    fun getGeneroById(id: Int, success: (Genero) -> Unit, failure: (Throwable) -> Unit) {
        service.getGeneroById(id).enqueue(object : Callback<Genero> {
            override fun onResponse(call: Call<Genero>, response: Response<Genero>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    failure(Throwable("Error ${response.code()} occurred"))
                }
            }

            override fun onFailure(call: Call<Genero>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertGenero(genero: Genero, success: (Genero) -> Unit, failure: (Throwable) -> Unit) {
        service.insertGenero(genero).enqueue(object : Callback<Genero> {
            override fun onResponse(call: Call<Genero>, response: Response<Genero>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    failure(Throwable("Error ${response.code()} occurred"))
                }
            }

            override fun onFailure(call: Call<Genero>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateGenero(genero: Genero, id: Int, success: (Genero) -> Unit, failure: (Throwable) -> Unit) {
        service.updateGenero(genero, id).enqueue(object : Callback<Genero> {
            override fun onResponse(call: Call<Genero>, response: Response<Genero>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    failure(Throwable("Error ${response.code()} occurred"))
                }
            }

            override fun onFailure(call: Call<Genero>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteGenero(id: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.deleteGenero(id).enqueue(object : Callback<Void> {
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

    fun getGeneroList(success: (List<Genero>) -> Unit, failure: (Throwable) -> Unit) {
        service.getGeneros().enqueue(object : Callback<List<Genero>> {
            override fun onResponse(call: Call<List<Genero>>, response: Response<List<Genero>>) {
                if (response.isSuccessful) {
                    response.body()?.let { success(it) }
                } else {
                    failure(Throwable("Error ${response.code()} occurred"))
                }
            }

            override fun onFailure(call: Call<List<Genero>>, t: Throwable) {
                failure(t)
            }
        })
    }
}