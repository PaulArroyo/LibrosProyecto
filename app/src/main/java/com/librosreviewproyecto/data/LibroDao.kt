package com.librosreviewproyecto.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.librosreviewproyecto.model.Libro


class LibroDao {

    //Variables usadas para la generacion de informacion en la nube
    private val coleccion1 = "librosApp"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private val coleccion2 = "misLibros"

    //Para guardar la conexion con la base de datos
    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        //Inicializa la configuracion de Firestore
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    //Las funciones de bajo nivel para hacer un CRUD
    //Create Read Update Delete

    fun saveLugar(libro: Libro) {
        //Para definir un documento en la nube...
        val documento : DocumentReference

        if (libro.id.isEmpty()) { //Si esta vacio, es un nuevo documento
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document()
            libro.id = documento.id
        } else { //Si el id tiene algo, entonces se va a modificar ese documento (lugar)
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(libro.id)
        }

        //Ahora, se modifica o crea el documento
        documento.set(libro)
            .addOnSuccessListener {
                Log.d("saveLibro","Libro creado/actualizado")
            }
            .addOnCanceledListener {
                Log.e("saveLibro","Libro NO creado/actualizado")
            }
    }

    fun deleteLibro(libro: Libro) {
        //Se valida si el lugar tiene id, para poder borrarlo
        if (libro.id.isNotEmpty()) { //Si no esta vacio, se puede eliminar
            firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(libro.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("deleteLibro", "Libro eliminado")
                }
                .addOnCanceledListener {
                    Log.e("deleteLibro", "Libro NO eliminado")
                }
        }
    }



    fun getLibros() : MutableLiveData<List<Libro>> {
        val listaLibro = MutableLiveData<List<Libro>>()

        firestore
            .collection(coleccion1)
            .document(usuario)
            .collection(coleccion2)
            .addSnapshotListener { instantanea, e ->
                if (e != null) { //Se dio un error, capturando la imagen de info
                    return@addSnapshotListener
                }
                //Si estamos aca, no hubo error
                if (instantanea != null) { //Si se pudo recuperar la info
                    val lista = ArrayList<Libro>()

                    //Se recorre la instantanea documento por documento, conviertiendolo en Lugar y  agregandolo a la lista
                    instantanea.documents.forEach {
                        val libro = it.toObject(Libro::class.java)
                        if (libro != null) { //Si se pudo convertir el documento en un lugar
                            lista.add(libro) //Se agrega el lugar a la lista
                        }
                    }
                    listaLibro.value = lista
                }
            }
        return listaLibro
    }
}