package com.librosreviewproyecto.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "libro")
data class Libro(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ColumnInfo(name = "nombre")
    val nombre : String,
    @ColumnInfo(name = "autor")
    val autor : String,
    @ColumnInfo(name = "descrip")
    val descrip : String,
    @ColumnInfo(name="ruta_imagen")
    val rutaImagen : String?,
) : Parcelable
