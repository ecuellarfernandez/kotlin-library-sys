package com.example.practicaapipersonas.models

import java.io.Serializable

data class LibroData(
    val nombre: String,
    val autor: String,
    val editorial: String,
    val imagen: String,
    val sinopsis: String,
    val isbn: String,
    val calificacion: String
): Serializable {
    var id: Int? = null
}