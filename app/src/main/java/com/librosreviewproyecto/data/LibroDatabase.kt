package com.librosreviewproyecto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.librosreviewproyecto.model.Libro

@Database(entities = [Libro::class], version = 1, exportSchema = false)
abstract class LibroDatabase : RoomDatabase() {

    abstract fun libroDao() : LibroDao

    companion object {
        @Volatile
        private var INSTANCE : LibroDatabase? = null

        fun getDatabase(context: Context) : LibroDatabase {
            val local = INSTANCE
            if (local != null) {
                return local
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibroDatabase::class.java,
                    "libro_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}