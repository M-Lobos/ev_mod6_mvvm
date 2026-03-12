package com.lobosmanuel.myapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lobosmanuel.myapplication.databinding.ItemDollarBinding
import com.lobosmanuel.myapplication.model.local.DollarTransaction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Adaptador encargado de vincular la lista de DollarTransaction con el RecyclerView.
 * Implementa el patrón ViewHolder para optimizar el rendimiento del scroll.
 */
class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transaction = listOf<DollarTransaction>()

    /**
     * Actualiza el conjunto de datos del adaptador y notifica al RecyclerView para redibujar.
     */
    fun submitList(newList: List<DollarTransaction>) {
        transaction = newList
        notifyDataSetChanged() // Notifica un cambio total en el set de datos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemDollarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transaction[position])
    }

    override fun getItemCount(): Int = transaction.size

    /**
     * Clase interna que mantiene las referencias a los componentes visuales de cada fila.
     */
    inner class TransactionViewHolder(private val binding: ItemDollarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: DollarTransaction) {
            // Mapeo de datos a la UI
            binding.textTypeAmount.text = "${transaction.type} - ${transaction.amount} USD"

            // Formateo de timestamp (milisegundos) a texto legible
            val formattedDate = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
            ).format(Date(transaction.timestamp))

            binding.textTimestamp.text = formattedDate
        }
    }
}