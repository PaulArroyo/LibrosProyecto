package com.librosreviewproyecto.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.librosreviewproyecto.model.Biblioteca

class BibliotecaDao {

    //Variables usadas para poder generar la estructura en la nube...
    private val coleccion3 = "bibliotecaApp"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private val coleccion4 = "misBibliotecas"

    //Contiene la "conexion" a la base de datos
    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        //Inicializa la configuracion de Firestore
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun saveBiblioteca(biblioteca: Biblioteca) {
        //Para definir un documento en la nube...
        val documento : DocumentReference

        if (biblioteca.id.isEmpty()) { //Si esta vacio, es un nuevo documento
            documento = firestore
                .collection(coleccion3)
                .document(usuario)
                .collection(coleccion4)
                .document()
            biblioteca.id = documento.id
        } else { //Si el id tiene algo, entonces se va a modificar ese documento (biblioteca)
            documento = firestore
                .collection(coleccion3)
                .document(usuario)
                .collection(coleccion4)
                .document(biblioteca.id)
        }

        //Ahora, se modifica o crea el documento
        documento.set(biblioteca)
            .addOnSuccessListener {
                Log.d("saveBiblioteca","Biblioteca creado/actualizado")
            }
            .addOnCanceledListener {
                Log.e("saveBiblioteca","Biblioteca NO creado/actualizado")
            }
    }

    fun deleteBiblioteca(biblioteca: Biblioteca) {
        //Se valida si el lugar tiene id, para poder borrarlo
                if (biblioteca.id.isNotEmpty()) { //Si no esta vacio, se puede eliminar
                    firestore
                        .collection(coleccion3)
                        .document(usuario)
                        .collection(coleccion4)
                        .document(biblioteca.id)
                        .delete()
                        .addOnSuccessListener {
                            Log.d("deleteBiblioteca", "Biblioteca eliminada")
                        }
                        .addOnCanceledListener {
                            Log.e("deleteBiblioteca", "Biblioteca NO eliminada")
                        }
                }
            }


    fun getBibliotecas(): MutableLiveData<List<Biblioteca>> {
            val listaBibliotecas = MutableLiveData<List<Biblioteca>>()

            firestore
                .collection(coleccion3)
                .document(usuario)
                .collection(coleccion4)
                .addSnapshotListener { instantanea, e ->
                    if (e != null) { //Se dio un error, capturando la imagen de info
                        return@addSnapshotListener
                    }
                    //Si estamos aca, no hubo error
                    if (instantanea != null) { //Si se pudo recuperar la info
                        val lista = ArrayList<Biblioteca>()

                        //Se recorre la instantanea documento por documento, conviertiendolo en Lugar y  agregandolo a la lista
                        instantanea.documents.forEach {
                            val biblioteca = it.toObject(Biblioteca::class.java)
                            if (biblioteca != null) { //Si se pudo convertir el documento en un lugar
                                lista.add(biblioteca) //Se agrega el lugar a la lista
                            }
                        }
                        listaBibliotecas.value = lista
                    }
                }
            return listaBibliotecas

    }

}