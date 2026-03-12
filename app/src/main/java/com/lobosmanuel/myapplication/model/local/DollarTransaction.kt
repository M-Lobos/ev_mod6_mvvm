package com.lobosmanuel.myapplication.model.local

//Importar dependencias desde room
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * DollarTransaction es una DataClass; clase diseñada específicamente para almacenar
 * datos, eliminando la necesidad de escribir código repetitivo
 *
 * Representa la tabla de transacciones en la base de datos local.
 * Cada instancia de esta data class equivale a una fila en 'dolar_transacctions'.
 * Cada atributo a una columna
 */
@Entity(tableName = "dolar_transacctions")
data class DollarTransaction(
    /**
     * Clave primaria autoincremental.
     * Se inicializa en 0 para que Room genere el ID automáticamente al insertar.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * Naturaleza de la operación.
     * Valores esperados: "BUY" para compra, "SELL" para venta.
     */
    val type: String,

    /**
     * Monto de la transacción en formato decimal.
     */
    val amount: Double,

    /**
     * Marca de tiempo en milisegundos.
     * Por defecto captura el momento exacto de la creación del objeto.
     */
    val timestamp: Long = System.currentTimeMillis()
)