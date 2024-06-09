package com.example.practicaapipersonas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicaapipersonas.databinding.LibroItemLayoutBinding
import com.example.practicaapipersonas.models.Libro

class LibroAdapter(private val libroList: MutableList<Libro>, private val listener: OnLibroClickListener) :
    RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val binding =
            LibroItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LibroViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return libroList.size
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libroList[position]
        holder.bind(libro, listener)
    }

    fun updateData(libroList: List<Libro>) {
        this.libroList.clear()
        this.libroList.addAll(libroList)
        notifyDataSetChanged()
    }

    class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(libro: Libro, listener: OnLibroClickListener) {
            val binding = LibroItemLayoutBinding.bind(itemView)
            binding.apply {
                lblLibroName.text = libro.nombre
                val calificaciontxt = libro.calificacion.toInt()
                lblCalificacionLibroMain.text = "${calificaciontxt}/10"
                lblAutorItemMain.text = libro.autor
                Glide.with(itemView.context)
                    .load(libro.imagen)
                    .into(imgLibro)
                btnDeleteLibroItem.setOnClickListener {
                    listener.onLibroDelete(libro)
                }
                btnEditarLibroItem.setOnClickListener {
                    listener.onLibroEdit(libro)
                }
                lblLibroName.setOnClickListener {
                    listener.onLibroClick(libro)
                }
                root.setOnClickListener { listener.onLibroClick(libro) }
            }
        }
    }

    interface OnLibroClickListener {
        fun onLibroClick(libro: Libro)
        fun onLibroDelete(libro: Libro)
        fun onLibroEdit(libro: Libro)
    }
}