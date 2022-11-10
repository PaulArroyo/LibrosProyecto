package com.librosreviewproyecto.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.librosreviewproyecto.model.Libro

@Dao
interface LibroDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLibro(libro: Libro)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateLibro(libro: Libro)

    @Delete
    suspend fun deleteLibro(libro: Libro)

    @Query("SELECT * FROM LIBRO")
    fun getLibros() : LiveData<List<Libro>>
}