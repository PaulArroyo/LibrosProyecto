package com.librosreviewproyecto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.librosreviewproyecto.databinding.LibroFilaBinding
import com.librosreviewproyecto.model.Libro
import com.librosreviewproyecto.ui.libro.LibroFragmentDirections

class LibroAdapter : RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    inner class LibroViewHolder(private val itemBinding : LibroFilaBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
            fun dibuja(libro: Libro) {
                itemBinding.tvNombre.text = libro.nombre
                itemBinding.tvAutor.text = libro.autor
                itemBinding.tvDescrip.text = libro.descrip
                itemBinding.vistaFila.setOnClickListener {
                    val action = LibroFragmentDirections
                        .actionNavLibroToUpdateLibroFragment(libro)
                    itemView.findNavController().navigate(action)
                }
            }
        }

    private var listaLibros = emptyList<Libro>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val itemBinding = LibroFilaBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return LibroViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = listaLibros[position]
        holder.dibuja(libro)
    }

    override fun getItemCount(): Int {
        return listaLibros.size
    }

    fun setListaLibros(libros : List<Libro>) {
        this.listaLibros = libros
        notifyDataSetChanged()
    }
}