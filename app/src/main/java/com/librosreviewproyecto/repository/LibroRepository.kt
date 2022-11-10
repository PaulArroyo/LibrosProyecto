package com.librosreviewproyecto.repository

import androidx.lifecycle.LiveData
import com.librosreviewproyecto.data.LibroDao
import com.librosreviewproyecto.model.Libro

class LibroRepository(private val libroDao: LibroDao) {

    suspend fun saveLibro(libro: Libro) {
        if (libro.id==0) {
            libroDao.addLibro(libro)
        } else {
            libroDao.updateLibro(libro)
        }
    }

    suspend fun deleteLibro(libro: Libro) {
        if (libro.id!=0) {
            libroDao.deleteLibro(libro)
        }
    }

    val getLibros : LiveData<List<Libro>> = libroDao.getLibros()
}