package com.librosreviewproyecto.ui.libro

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.librosreviewproyecto.R
import com.librosreviewproyecto.databinding.FragmentAddLibroBinding
import com.librosreviewproyecto.model.Libro
import com.librosreviewproyecto.viewmodel.LibroViewModel
import com.librosreviewproyecto.utiles.ImagenUtiles


class AddLibroFragment : Fragment() {

    private lateinit var libroViewModel: LibroViewModel
    private var _binding : FragmentAddLibroBinding? = null
    private val binding get() = _binding!!

    private lateinit var imagenUtiles: ImagenUtiles
    private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        libroViewModel = ViewModelProvider(this)[LibroViewModel::class.java]
        _binding = FragmentAddLibroBinding.inflate(inflater, container, false)
        binding.btAdd.setOnClickListener {
            binding.progressBar.visibility = ProgressBar.VISIBLE
            binding.msgMensaje.text = getString(R.string.msg_subiendo_imagen)
            binding.msgMensaje.visibility = TextView.VISIBLE
            subeImagen()
        }

        tomarFotoActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                imagenUtiles.actualizaFoto()
            }
        }

        imagenUtiles = ImagenUtiles(
            requireContext(),
            binding.btPhoto,
            binding.imagen,
            tomarFotoActivity)

        return binding.root
    }

    private fun subeImagen() {
        binding.msgMensaje.text = getString(R.string.msg_subiendo_imagen)

        val archivoLocal = imagenUtiles.imagenFile
        if (archivoLocal.exists() &&
            archivoLocal.isFile &&
            archivoLocal.canRead()) {

            val rutaLocal = Uri.fromFile(archivoLocal)
            val rutaNube = "librosApp/${Firebase.auth.currentUser?.email}/imagenes/${archivoLocal.name}"
            val referencia: StorageReference = Firebase.storage.reference.child(rutaNube)

            referencia.putFile(rutaLocal)
                .addOnSuccessListener {
                    referencia.downloadUrl
                        .addOnSuccessListener {
                            val rutaImagen = it.toString()
                            addLibro(rutaImagen)
                        }
                }
                .addOnFailureListener {
                    addLibro("")
                }
        } else {
            addLibro("")
        }
    }


    private fun addLibro(rutaImagen: String) {
        binding.msgMensaje.text = getString(R.string.msg_subiendo_libro)
        val nombre = binding.etNombre.text.toString()
        if (nombre.isNotEmpty()) {
            val autor = binding.etAutor.text.toString()
            val descrip = binding.etDescrip.text.toString()
            val libro = Libro("", nombre, autor, descrip, rutaImagen)

            libroViewModel.saveLibro(libro)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_libro_added),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_addLibroFragment_to_nav_libro)
        } else {
            Toast.makeText(requireContext(),
            getString(R.string.msg_data),
            Toast.LENGTH_LONG).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}