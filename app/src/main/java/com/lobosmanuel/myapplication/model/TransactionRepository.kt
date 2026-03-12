package com.lobosmanuel.myapplication.model

import com.lobosmanuel.myapplication.model.local.DollarTransaction
import com.lobosmanuel.myapplication.model.local.DollarTransactionDao

/**
 * Clase que abstrae el acceso a los datos para el resto de la aplicación.
 * El Repository decide si los datos provienen de una base de datos local,
 * una API remota o un caché, CENTRALIZA la lógica de origen de datos.
 */
class TransactionRepository(private val dao: DollarTransactionDao) {

    /**
     * Delega la inserción de una transacción al DAO.
     * @param transaction Objeto con los detalles de la operación.
     */
    suspend fun insertTransaction(transaction: DollarTransaction) {
        dao.insertTransaction(transaction)
    }

    /**
     * Recupera el listado completo de transacciones desde el DAO.
     * @return Lista de transacciones ordenadas según la lógica del DAO.
     */
    suspend fun getAllTransactions(): List<DollarTransaction> {
        return dao.getAllTransactions()
    }
}