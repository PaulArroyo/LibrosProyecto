package com.librosreviewproyecto.ui.libro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.librosreviewproyecto.R
import com.librosreviewproyecto.databinding.FragmentAddLibroBinding
import com.librosreviewproyecto.model.Libro
import com.librosreviewproyecto.viewmodel.LibroViewModel


class AddLibroFragment : Fragment() {

    private lateinit var libroViewModel: LibroViewModel
    private var _binding : FragmentAddLibroBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        libroViewModel = ViewModelProvider(this)[LibroViewModel::class.java]

        _binding = FragmentAddLibroBinding.inflate(inflater, container, false)

        binding.btAdd.setOnClickListener { addLibro() }

        return binding.root
    }

    private fun addLibro() {
        val nombre = binding.etNombre.text.toString()
        if (nombre.isNotEmpty()) {
            val autor = binding.etAutor.text.toString()
            val descrip = binding.etDescrip.text.toString()
            val libro = Libro(0, nombre, autor, descrip, "")

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