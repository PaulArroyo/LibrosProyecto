package com.librosreviewproyecto.repository

import androidx.lifecycle.MutableLiveData
import com.librosreviewproyecto.data.BibliotecaDao
import com.librosreviewproyecto.model.Biblioteca

class BibliotecaRepository(private val bibliotecaDao: BibliotecaDao) {

    fun saveBiblioteca(biblioteca: Biblioteca) {
        bibliotecaDao.saveBiblioteca(biblioteca)
    }

    fun deleteBiblioteca(biblioteca: Biblioteca) {
        bibliotecaDao.deleteBiblioteca(biblioteca)
    }

    val getBibliotecas : MutableLiveData<List<Biblioteca>> = bibliotecaDao.getBibliotecas()
}