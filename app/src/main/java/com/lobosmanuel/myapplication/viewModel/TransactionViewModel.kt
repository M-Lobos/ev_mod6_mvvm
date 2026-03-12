
package com.lobosmanuel.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lobosmanuel.myapplication.model.TransactionRepository
import com.lobosmanuel.myapplication.model.local.DollarTransaction
import kotlinx.coroutines.launch
import kotlin.jvm.java

class TransactionViewModel (private  val repository: TransactionRepository): ViewModel(){


    // LiveData interna para almacenar la lista de transacciones
    // MutableLiveData permite actualizar los datos dentro del ViewModel
    private val _transactions = MutableLiveData<List<DollarTransaction>>()

    // Exposición de la lista de transacciones como LiveData solo lectura
    // Esto permite que la Activity observe los cambios sin poder modificarlos directamente

    val transaction: LiveData<List<DollarTransaction>> = _transactions


    // Función para agregar una nueva transacción

    fun addTransaction(type :String, amount : Double){
        // Creamos un objeto DollarTransaction con el tipo (BUY/SELL) y el monto

        val transaction = DollarTransaction( type = type, amount = amount)
        // Lanzamos una corrutina para insertar la transacción en la base de datos

        viewModelScope.launch {

            repository.insertTransaction(transaction)
            // Después de insertar, recargamos la lista para reflejar los cambios
            loadTransactions()
        }



    }


    // Función para cargar todas las transacciones desde la base de datos

    fun loadTransactions(){
        // Lanzamos una corrutina dentro del viewModelScope
        // Esto evita bloquear el hilo principal (UI)
        viewModelScope.launch {
            // Obtenemos la lista de transacciones del repositorio
            val list = repository.getAllTransactions()
            // Actualizamos el LiveData, lo hacemos con postValue porque puede estar en un hilo en background
            _transactions.postValue(list)

        }


    }


}

// clases de factoty para crear View Model en capas superiores
class TransactionViewModelFactory (private val repository: TransactionRepository):
    ViewModelProvider.Factory{



    // Este método se llama cuando se necesita crear un ViewModel

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Comprobamos que el tipo de ViewModel que se solicita es TransactionViewModel

        if(modelClass.isAssignableFrom(com.lobosmanuel.myapplication.viewModel.TransactionViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")

            // Retornamos la instancia del ViewModel con el repositorio inyectado
            return TransactionViewModel(repository) as T


        }
        // Si se solicita un ViewModel desconocido, lanzamos excepción
        throw IllegalArgumentException("UnKnow ViewModel class")

    }









}




















