package com.lobosmanuel.myapplication.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * DollarTransactionDao es una interfaz que define qué hace el modelo de transaccion
 *
 * Interfaz que define los métodos de acceso a la base de datos (Data Access Object o DAO).
 * Proporciona el puente entre las llamadas de funciones de Kotlin y las consultas SQL.
 */
@Dao
interface DollarTransactionDao {

    /**
     * Inserta un nuevo registro en la tabla.
     * @param transaction Objeto que contiene los datos de la transacción (monto, tipo, etc.).
     * 'suspend' indica que esta operación debe ejecutarse fuera del hilo principal.
     */
    @Insert
    suspend fun insertTransaction(transaction: DollarTransaction)

    /**
     * Recupera todos los registros de la tabla 'dolar_transacctions'.
     * Ordena los resultados por el campo 'timestamp' de forma descendente (más recientes primero).
     * @return Una lista inmutable de objetos DollarTransaction.
     */
    @Query("SELECT * FROM dolar_transacctions ORDER BY timestamp DESC")
    suspend fun getAllTransactions(): List<DollarTransaction>
}