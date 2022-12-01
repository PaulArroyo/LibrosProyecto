package com.librosreviewproyecto.repository

import androidx.lifecycle.MutableLiveData
import com.librosreviewproyecto.data.LibroDao
import com.librosreviewproyecto.model.Libro

class LibroRepository(private val libroDao: LibroDao) {

    fun saveLibro(libro: Libro) {
        libroDao.saveLugar(libro)
    }

    fun deleteLibro(libro: Libro) {
        libroDao.deleteLibro(libro)
    }

    val getLibros : MutableLiveData<List<Libro>> = libroDao.getLibros()
}