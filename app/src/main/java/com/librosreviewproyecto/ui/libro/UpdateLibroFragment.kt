package com.librosreviewproyecto.ui.libro

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.librosreviewproyecto.R
import com.librosreviewproyecto.databinding.FragmentUpdateLibroBinding
import com.librosreviewproyecto.model.Libro
import com.librosreviewproyecto.viewmodel.LibroViewModel


class UpdateLibroFragment : Fragment() {

    private val args by navArgs<UpdateLibroFragmentArgs>()

    private lateinit var libroViewModel: LibroViewModel

    private var _binding: FragmentUpdateLibroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        libroViewModel = ViewModelProvider(this)[LibroViewModel::class.java]

        _binding = FragmentUpdateLibroBinding.inflate(inflater, container, false)

        binding.etNombre.setText(args.libro.nombre)
        binding.etAutor.setText(args.libro.autor)
        binding.etDescrip.setText(args.libro.descrip)

        binding.btUpdate.setOnClickListener { updateLibro() }
        binding.btDelete.setOnClickListener { deleteLibro() }

        if (args.libro.rutaImagen?.isNotEmpty()==true) {
            Glide.with(requireContext())
                .load(args.libro.rutaImagen)
                .fitCenter()
                .into(binding.imagen)
        }

        return binding.root
    }

    private fun deleteLibro() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.bt_delete_libro)
        alerta.setMessage(getString(R.string.msg_pregunta_delete)+ "${args.libro.nombre}?")
        alerta.setPositiveButton(getString(R.string.msg_si)) {_,_ ->
            libroViewModel.deleteLibro(args.libro)
            Toast.makeText(requireContext(),getString(R.string.msg_libro_deleted),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateLibroFragment_to_nav_libro)
        }
        alerta.setNegativeButton(getString(R.string.msg_no)) {_,_ ->}
        alerta.create().show()
    }

    private fun updateLibro() {
        val nombre = binding.etNombre.text.toString()
        if (nombre.isNotEmpty()) {
            val autor = binding.etAutor.text.toString()
            val descrip = binding.etDescrip.text.toString()
            val libro = Libro(args.libro.id, nombre, autor, descrip,
                args.libro.rutaImagen)

            libroViewModel.saveLibro(libro)
            Toast.makeText(requireContext(),
                getString(R.string.msg_libro_updated),
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