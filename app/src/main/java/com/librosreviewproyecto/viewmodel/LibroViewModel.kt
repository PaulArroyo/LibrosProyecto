package com.librosreviewproyecto.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.librosreviewproyecto.data.LibroDatabase
import com.librosreviewproyecto.model.Libro
import com.librosreviewproyecto.repository.LibroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibroViewModel(application: Application) : AndroidViewModel(application) {

    private val libroRepository : LibroRepository
    val getLibros : LiveData<List<Libro>>

    init {
        val libroDao = LibroDatabase.getDatabase(application).libroDao()
        libroRepository = LibroRepository(libroDao)
        getLibros = libroRepository.getLibros
    }

    fun saveLibro(libro: Libro) {
        viewModelScope.launch(Dispatchers.IO) {
            libroRepository.saveLibro(libro)
        }
    }

    fun deleteLibro(libro: Libro) {
        viewModelScope.launch(Dispatchers.IO) {
            libroRepository.deleteLibro(libro)
        }
    }
}