package com.example.practicaapipersonas.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicaapipersonas.models.Libro
import com.example.practicaapipersonas.models.Genero
import com.example.practicaapipersonas.repositories.GeneroRepository
import com.example.practicaapipersonas.repositories.LibroRepository

class MainViewModel : ViewModel() {
    private val _libroList: MutableLiveData<List<Libro>> by lazy {
        MutableLiveData<List<Libro>>()
    }
    val libroList: LiveData<List<Libro>> get() = _libroList

    private val _generoList: MutableLiveData<List<Genero>> by lazy {
        MutableLiveData<List<Genero>>()
    }
    val generoList: LiveData<List<Genero>> get() = _generoList

    fun fetchListaLibros() {
        LibroRepository.getLibroList(
            success = { libros ->
                libros?.let {
                    _libroList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun fetchListaGeneros() {
        GeneroRepository.getGeneroList(
            success = { generos ->
                generos?.let {
                    _generoList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })
    }
}