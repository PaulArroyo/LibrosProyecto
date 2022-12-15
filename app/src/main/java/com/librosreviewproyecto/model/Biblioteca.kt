package com.librosreviewproyecto.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Biblioteca(
    var id: String,
    val nombre: String,
    val telefono: String?,
    val web: String?,
    val latitud: Double?,
    val longitud: Double?,
    val altura: Double?
) : Parcelable {
    constructor():
            this("","","","",0.0,0.0,0.0)
}
