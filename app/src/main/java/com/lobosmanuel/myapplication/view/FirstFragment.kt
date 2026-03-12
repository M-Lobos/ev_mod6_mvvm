package com.lobosmanuel.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lobosmanuel.myapplication.databinding.FragmentFirstBinding
import com.lobosmanuel.myapplication.model.TransactionRepository
import com.lobosmanuel.myapplication.model.local.AppDataBase
import com.lobosmanuel.myapplication.viewModel.TransactionViewModel
import com.lobosmanuel.myapplication.viewModel.TransactionViewModelFactory

/**
 * A simple [androidx.fragment.app.Fragment] subclass as the default destination in the navigation.
 */
/**
 * Vista (Fragment) encargada de la interacción con el usuario.
 * Implementa la observación reactiva del ViewModel y gestiona el ciclo de vida de la UI.
 */
class FirstFragment : Fragment() {

    // Manejo de View Binding con nulabilidad para evitar fugas de memoria
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val adapter = TransactionAdapter()
    private lateinit var repository: TransactionRepository

    /**
     * Inicialización del ViewModel usando 'by viewModels' y la Factory.
     * Esto asegura que el ViewModel sobreviva a cambios de configuración (como rotar la pantalla).
     */
    private val viewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Inicialización de la capa de datos
        repository = TransactionRepository(AppDataBase.getDataBase(requireContext()).dollarTransactionDao())

        // 2. Configuración del RecyclerView
        binding.transactionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.transactionRecyclerView.adapter = adapter

        // 3. Suscripción al "Cerebro" (ViewModel)
        // Cada vez que la lista en Room cambie, el Observer lo detectará automáticamente
        viewModel.transactions.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        // Carga inicial de datos
        viewModel.loadTransactions()

        // 4. Listeners de botones (Disparadores de eventos)
        binding.buyButton.setOnClickListener { handleTransacction("BUY") }
        binding.sellButton.setOnClickListener { handleTransacction("SELL") } // Corrección aplicada
    }

    /**
     * Procesa la entrada del usuario, valida el contenido y envía la acción al ViewModel.
     */
    private fun handleTransacction(type: String) {
        val amountText = binding.amountEditText.text.toString()

        if (amountText.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese un monto", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDouble()
        viewModel.addTransaction(type, amount) // El fragmento no sabe CÓMO se guarda, solo da la orden

        binding.amountEditText.text.clear()
        Toast.makeText(requireContext(), "$type $amount USD registrado", Toast.LENGTH_SHORT).show()
    }
}