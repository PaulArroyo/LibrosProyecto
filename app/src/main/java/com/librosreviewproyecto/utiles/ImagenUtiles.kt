package com.librosreviewproyecto.utiles

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import com.librosreviewproyecto.BuildConfig
import java.io.File

class ImagenUtiles(
    private val contexto: Context,
    btPhoto: Button,
    private val imagen: ImageView,
    private var tomarFotoActivity: ActivityResultLauncher<Intent>
) {
    init {
        btPhoto.setOnClickListener { tomarFoto() }
    }

    lateinit var imagenFile: File
    private lateinit var currentPhotoPath: String

    @SuppressLint("QueryPermissionsNeeded")
    private fun tomarFoto() {
        val intent = Intent(Intent.ACTION_PICK)
        if (intent.resolveActivity(contexto.packageManager) != null) {
            imagenFile = createImageFile()
            val photoURI = FileProvider.getUriForFile(
                contexto,
                BuildConfig.APPLICATION_ID + ".provider",
                imagenFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            tomarFotoActivity.launch(intent)
        }
    }

    private fun createImageFile(): File {
        val archivo= OtrosUtiles.getTempFile("imagen_")
        val storageDir: File? =
            contexto.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image= File.createTempFile(
            archivo,       /* prefijo */
            ".jpg",  /* extensión */
            storageDir    /* directorio */)
        currentPhotoPath = image.absolutePath
        return image
    }

    fun actualizaFoto() {
        imagen.setImageBitmap(
            BitmapFactory.decodeFile(imagenFile.absolutePath))
    }
}








