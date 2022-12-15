package com.librosreviewproyecto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.librosreviewproyecto.databinding.BibliotecaFilaBinding
import com.librosreviewproyecto.model.Biblioteca
import com.librosreviewproyecto.ui.biblioteca.BibliotecaFragmentDirections

class BibliotecaAdapter : RecyclerView.Adapter<BibliotecaAdapter.BibliotecaViewHolder>() {

    //Clase interna que se encarga de finalmente dibujar la informacion
    inner class BibliotecaViewHolder(private val itemBinding : BibliotecaFilaBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun dibuja(biblioteca: Biblioteca) {
            itemBinding.tvNombreBiblio.text = biblioteca.nombre
            itemBinding.tvTelefono.text = biblioteca.telefono
            itemBinding.tvWeb.text = biblioteca.web

            itemBinding.vistaFila.setOnClickListener {
                // Crea una accion para navegar a updateLugar pasando un argumento biblioteca
                val action = BibliotecaFragmentDirections
                    .actionNavBibliotecaToUpdateBibliotecaFragment(biblioteca)

                // efectivamente se pasa al fragmento...
                itemView.findNavController().navigate(action)
            }
        }
    }

    //La lista donde estan los objetos Lugar a dibujarse...
    private var listaLugares = emptyList<Biblioteca>()

    //Esta funcion crea cajitas para cada lugar... en memoria
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibliotecaViewHolder {
        val itemBinding = BibliotecaFilaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return BibliotecaViewHolder(itemBinding)
    }

    //Esta funcion toma un lugar y lo envia a dibujar...
    override fun onBindViewHolder(holder: BibliotecaViewHolder, position: Int) {
        val lugar = listaLugares[position]
        holder.dibuja(lugar)
    }

    //Esta funcuin devuelve loa cantidad de elementos a dibujar... (cajitas)
    override fun getItemCount(): Int {
        return listaLugares.size
    }

    fun setListaLugares(lugares: List<Biblioteca>) {
        this.listaLugares = lugares
        notifyDataSetChanged()
    }
}