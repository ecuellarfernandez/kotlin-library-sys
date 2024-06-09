package com.example.practicaapipersonas.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaapipersonas.R
import com.example.practicaapipersonas.models.Genero
import com.example.practicaapipersonas.ui.activities.MainActivity
import com.google.android.material.button.MaterialButton // Import MaterialButton

class GeneroAdapter(private var generos: MutableList<Genero>) : RecyclerView.Adapter<GeneroAdapter.GeneroViewHolder>() {

    class GeneroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val generoName: TextView = itemView.findViewById(R.id.generoName)
        val btnDeleteGenero: MaterialButton = itemView.findViewById(R.id.btnDeleteGeneroItem) // Change ImageButton to MaterialButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genero_item_layout, parent, false)
        return GeneroViewHolder(view)
    }

    override fun onBindViewHolder(holder: GeneroViewHolder, position: Int) {
        val genero = generos[position]
        holder.generoName.text = genero.nombre
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, MainActivity::class.java)
            intent.putExtra("generoId", genero.id)
            it.context.startActivity(intent)
        }
        holder.btnDeleteGenero.setOnClickListener {
            // Handle genre deletion here
            generos.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = generos.size

    fun updateData(newGeneros: MutableList<Genero>) {
        this.generos = newGeneros
        notifyDataSetChanged()
    }
}