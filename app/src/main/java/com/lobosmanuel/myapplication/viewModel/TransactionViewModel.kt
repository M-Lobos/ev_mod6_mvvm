package com.lobosmanuel.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lobosmanuel.myapplication.model.TransactionRepository
import com.lobosmanuel.myapplication.model.local.DollarTransaction
import kotlinx.coroutines.launch

/**
 * Gestiona la lógica de negocio y el estado de la UI para las transacciones de dólares.
 * Actúa como puente entre el Repositorio y la Vista (Fragment/Activity).
 */
class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    /**
     * Fuente de datos privada y mutable. 
     * Encapsula el estado para que solo el ViewModel pueda modificarlo.
     */
    private val _transactions = MutableLiveData<List<DollarTransaction>>()

    /**
     * Flujo de datos público y de solo lectura.
     * La Vista se suscribe a este LiveData para reaccionar a los cambios de datos.
     */
    val transactions: LiveData<List<DollarTransaction>> = _transactions

    /**
     * Crea e inserta una nueva transacción de forma asíncrona.
     * @param type Tipo de operación ("BUY" o "SELL").
     * @param amount Cantidad de dólares operada.
     */
    fun addTransaction(type: String, amount: Double) {
        val newTransaction = DollarTransaction(type = type, amount = amount)

        // viewModelScope se cancela automáticamente si el ViewModel se destruye
        viewModelScope.launch {
            repository.insertTransaction(newTransaction)
            // Sincroniza la UI inmediatamente después de la persistencia
            loadTransactions()
        }
    }

    /**
     * Recupera el historial de transacciones desde el repositorio.
     * Utiliza postValue para asegurar que la actualización del LiveData ocurra en el hilo principal.
     */
    fun loadTransactions() {
        viewModelScope.launch {
            val list = repository.getAllTransactions()
            _transactions.postValue(list)
        }
    }
}

/**
 * Clase responsable de instanciar el ViewModel.
 * Necesaria porque TransactionViewModel requiere un constructor con parámetros.
 */
class TransactionViewModelFactory(private val repository: TransactionRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}