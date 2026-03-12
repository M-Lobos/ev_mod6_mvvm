package com.lobosmanuel.myapplication.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Punto de acceso principal a la base de datos persistente.
 * Define la configuración de las entidades y la versión del esquema.
 */
@Database(entities = [DollarTransaction::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    /**
     * Proporciona acceso a los métodos definidos en el DAO.
     */
    abstract fun dollarTransactionDao(): DollarTransactionDao

    companion object {
        /**
         * '@Volatile' asegura que el valor de INSTANCE esté siempre actualizado
         * para todos los hilos de ejecución, evitando lecturas de caché obsoletas.
         */
        @Volatile
        private var INSTANCE: AppDataBase? = null

        /**
         * Retorna la instancia existente o crea una nueva bajo demanda.
         * Implementa el patrón Singleton para garantizar una única conexión.
         */
        fun getDataBase(context: Context): AppDataBase {
            // Retorno temprano si la instancia ya existe
            return INSTANCE ?: synchronized(this) {
                // 'synchronized' evita que dos hilos creen dos bases de datos al mismo tiempo
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "dollar_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}