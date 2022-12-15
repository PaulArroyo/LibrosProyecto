package com.librosreviewproyecto.ui.biblioteca

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.librosreviewproyecto.R
import com.librosreviewproyecto.databinding.FragmentAddBibliotecaBinding
import com.librosreviewproyecto.databinding.FragmentAddLibroBinding
import com.librosreviewproyecto.model.Biblioteca
import com.librosreviewproyecto.model.Libro
import com.librosreviewproyecto.viewmodel.LibroViewModel
import com.librosreviewproyecto.utiles.ImagenUtiles
import com.librosreviewproyecto.viewmodel.BibliotecaViewModel


class AddBibliotecaFragment : Fragment() {

    private lateinit var bibliotecaViewModel: BibliotecaViewModel
    private var _binding : FragmentAddBibliotecaBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bibliotecaViewModel = ViewModelProvider(this)[BibliotecaViewModel::class.java]
        _binding = FragmentAddBibliotecaBinding.inflate(inflater, container, false)
        binding.btAdd.setOnClickListener {
            binding.progressBar.visibility = ProgressBar.VISIBLE
            binding.msgMensaje.text = getString(R.string.msg_subiendo_biblioteca)
            binding.msgMensaje.visibility = TextView.VISIBLE
            addBiblioteca()
        }
        binding.btLocation.setOnClickListener{
            binding.progressBar.visibility = ProgressBar.VISIBLE
            binding.msgMensaje.text = getString(R.string.msg_guardando_locacion)
            binding.msgMensaje.visibility = TextView.VISIBLE
            activaGPS()
        }

        return binding.root
    }

    private fun activaGPS() {
        if (requireActivity()
                .checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED
            && requireActivity()
                .checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED) {
            //Si estamos aca hay que pedir autorizacion para hacer la ubicacion gps
            requireActivity()
                .requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION),105)
        } else {
            //Si se tienen los permisos se busca la ubicacion gps
            val ubicacion: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            ubicacion.lastLocation.addOnSuccessListener {
                    location: Location? ->
                if (location != null) {
                    binding.tvLatitud.text = "${location.latitude}"
                    binding.tvLongitud.text = "${location.longitude}"
                    binding.tvAltura.text = "${location.altitude}"
                } else {
                    binding.tvLatitud.text = "0.0"
                    binding.tvLongitud.text = "0.0"
                    binding.tvAltura.text = "0.0"
                }
            }
        }
    }

    private fun addBiblioteca() {
        binding.msgMensaje.text = getString(R.string.msg_subiendo_biblioteca)
        val nombre = binding.etNombreBiblio.text.toString()
        if (nombre.isNotEmpty()) {
            val telefono = binding.etTelefono.text.toString()
            val web = binding.etWeb.text.toString()
            val latitud = binding.tvLatitud.text.toString().toDouble()
            val longitud = binding.tvLongitud.text.toString().toDouble()
            val altura = binding.tvAltura.text.toString().toDouble()

            val biblioteca = Biblioteca("", nombre, telefono, web, latitud, longitud, altura)

            bibliotecaViewModel.saveBiblioteca(biblioteca)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_biblio_added),
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