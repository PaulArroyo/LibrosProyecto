package com.librosreviewproyecto.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Libro(
    var id : String,
    val nombre : String,
    val autor : String,
    val descrip : String,
    val rutaImagen : String?,
) : Parcelable {
    constructor():
                this("","","","","")
}
