package com.librosreviewproyecto.ui.biblioteca

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.librosreviewproyecto.R
import com.librosreviewproyecto.adapter.LibroAdapter
import com.librosreviewproyecto.databinding.FragmentLibroBinding
import com.librosreviewproyecto.viewmodel.LibroViewModel

class BibliotecaFragment : Fragment() {

    private var _binding: FragmentLibroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val libroViewModel =
            ViewModelProvider(this)[LibroViewModel::class.java]

        _binding = FragmentLibroBinding.inflate(inflater, container, false)

        binding.addLibroFabButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_libro_to_addLibroFragment)
        }

        val libroAdapter = LibroAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = libroAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        libroViewModel.getLibros.observe(viewLifecycleOwner) {
            libros -> libroAdapter.setListaLibros(libros)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}