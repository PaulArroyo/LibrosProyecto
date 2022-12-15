package com.librosreviewproyecto.ui.biblioteca

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.librosreviewproyecto.R
import com.librosreviewproyecto.databinding.FragmentUpdateBibliotecaBinding
import com.librosreviewproyecto.databinding.FragmentUpdateLibroBinding
import com.librosreviewproyecto.model.Biblioteca
import com.librosreviewproyecto.model.Libro
import com.librosreviewproyecto.viewmodel.BibliotecaViewModel
import com.librosreviewproyecto.viewmodel.LibroViewModel


class UpdateBibliotecaFragment : Fragment() {

    private val args by navArgs<UpdateBibliotecaFragmentArgs>()

    private lateinit var bibliotecaViewModel: BibliotecaViewModel

    private var _binding: FragmentUpdateBibliotecaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bibliotecaViewModel = ViewModelProvider(this)[BibliotecaViewModel::class.java]

        _binding = FragmentUpdateBibliotecaBinding.inflate(inflater, container, false)

        binding.etNombreBiblio.setText(args.biblioteca.nombre)
        binding.etTelefono.setText(args.biblioteca.telefono)
        binding.etWeb.setText(args.biblioteca.web)

        binding.btUpdate.setOnClickListener { updateBiblioteca() }
        binding.btDelete.setOnClickListener { deleteBiblioteca() }

        return binding.root
    }

    private fun verWeb() {
        val valor = binding.etWeb.text.toString()
        if (valor.isNotEmpty()) { //Si el valor tiene algo... entonces se intenta enviar el mensaje
            val uri = "http://$valor"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        } else { //Si no hay info no se puede realizar la accion
            Toast.makeText(requireContext(),
                getString(R.string.msg_data),Toast.LENGTH_LONG).show()
        }
    }

    private fun VerEnMapa() {
        val latitud = binding.tvLatitud.text.toString().toDouble()
        val longitud = binding.tvLongitud.text.toString().toDouble()
        if (latitud.isFinite() && longitud.isFinite()) {
            val uri = "geo:$latitud,$longitud?z18"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
    }

    private fun enviarWhatsapp() {
        val valor = binding.etTelefono.text.toString()
        if (valor.isNotEmpty()) { //Si el valor tiene algo... entonces se intenta enviar el mensaje
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = "whatsapp://send?phone=506$valor&text="+
                    getString(R.string.msg_saludos)
            intent.setPackage("com.whatsapp")
            intent.data = Uri.parse(uri)
            startActivity(intent)
        } else { //Si no hay info no se puede realizar la accion
            Toast.makeText(requireContext(),
                getString(R.string.msg_data),Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteBiblioteca() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.bt_delete_biblio)
        alerta.setMessage(getString(R.string.msg_pregunta_delete)+ "${args.biblioteca.nombre}?")
        alerta.setPositiveButton(getString(R.string.msg_si)) {_,_ ->
            bibliotecaViewModel.deleteBiblioteca(args.biblioteca)
            Toast.makeText(requireContext(),getString(R.string.msg_biblio_deleted),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateLibroFragment_to_nav_libro)
        }
        alerta.setNegativeButton(getString(R.string.msg_no)) {_,_ ->}
        alerta.create().show()
    }

    private fun updateBiblioteca() {
        val nombre = binding.etNombreBiblio.text.toString()
        if (nombre.isNotEmpty()) {
            val telefono = binding.etTelefono.text.toString()
            val web = binding.etWeb.text.toString()
            val latitud = binding.tvLatitud.text.toString().toDouble()
            val longitud = binding.tvLongitud.text.toString().toDouble()
            val altura = binding.tvAltura.text.toString().toDouble()

            val biblioteca = Biblioteca("", nombre, telefono, web, latitud, longitud, altura)

            bibliotecaViewModel.saveBiblioteca(biblioteca)
            Toast.makeText(requireContext(),
                getString(R.string.msg_biblio_updated),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_addBibliotecaFragment_to_nav_biblioteca)
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