package com.example.practicaapipersonas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaapipersonas.R
import com.example.practicaapipersonas.models.Genero
import com.google.android.material.button.MaterialButton

class GeneroAdapter(private var generos: MutableList<Genero>, private val listener: OnGeneroClickListener) : RecyclerView.Adapter<GeneroAdapter.GeneroViewHolder>() {

    class GeneroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val generoName: TextView = itemView.findViewById(R.id.generoName)
        val btnEditarGenero: MaterialButton = itemView.findViewById(R.id.btnEditarGeneroItem)
        val btnDeleteGenero: MaterialButton = itemView.findViewById(R.id.btnDeleteGeneroItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genero_item_layout, parent, false)
        return GeneroViewHolder(view)
    }

    override fun onBindViewHolder(holder: GeneroViewHolder, position: Int) {
        val genero = generos[position]
        holder.generoName.text = genero.nombre
        holder.itemView.setOnClickListener {
            listener.onGeneroClick(genero)
        }

        holder.btnEditarGenero.setOnClickListener {
            listener.onGeneroEdit(genero)
        }

        holder.btnDeleteGenero.setOnClickListener {
            listener.onGeneroDelete(genero)
            generos.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = generos.size

    fun updateData(newGeneros: MutableList<Genero>) {
        this.generos = newGeneros
        notifyDataSetChanged()
    }

    interface OnGeneroClickListener {
        fun onGeneroClick(genero: Genero)
        fun onGeneroEdit(genero: Genero)
        fun onGeneroDelete(genero: Genero)
    }
}