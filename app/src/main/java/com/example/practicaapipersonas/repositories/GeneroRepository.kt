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

    fun getGeneroById(id: Int) = service.getGeneroById(id)
    fun insertGenero(genero: Genero) = service.insertGenero(genero)
    fun updateGenero(genero: Genero, id: Int) = service.updateGenero(genero, id)
    fun deleteGenero(id: Int) = service.deleteGenero(id)
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