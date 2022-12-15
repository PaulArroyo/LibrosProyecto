package com.librosreviewproyecto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.librosreviewproyecto.data.BibliotecaDao
import com.librosreviewproyecto.model.Biblioteca
import com.librosreviewproyecto.repository.BibliotecaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BibliotecaViewModel(application: Application) : AndroidViewModel(application) {


    private val bibliotecaRepository: BibliotecaRepository = BibliotecaRepository(BibliotecaDao())

    val getBibliotecas: MutableLiveData<List<Biblioteca>> = bibliotecaRepository.getBibliotecas

    fun saveBiblioteca(biblioteca: Biblioteca) {
        viewModelScope.launch(Dispatchers.IO) {
            bibliotecaRepository.saveBiblioteca(biblioteca)
        }
    }
    fun deleteBiblioteca(biblioteca: Biblioteca) {
        viewModelScope.launch(Dispatchers.IO) {
            bibliotecaRepository.deleteBiblioteca(biblioteca)
        }
    }
}