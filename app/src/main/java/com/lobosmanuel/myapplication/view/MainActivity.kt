package com.lobosmanuel.myapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lobosmanuel.myapplication.R
import com.lobosmanuel.myapplication.databinding.ActivityMainBinding

/**
 * Actividad principal que actúa como contenedor (Host) para los fragmentos de la aplicación.
 * Gestiona el ciclo de vida básico y la configuración de la interfaz global (Toolbar).
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de View Binding para acceso directo a los componentes del XML
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de la barra de herramientas (Action Bar)
        setSupportActionBar(binding.toolbar)

        // Instancia del fragmento inicial
        val firstFragment = FirstFragment()

        /**
         * Verificación de savedInstanceState:
         * Si es null, es la primera vez que se crea la actividad.
         * Evita que el fragmento se recree innecesariamente en rotaciones de pantalla.
         */
        if (savedInstanceState == null) {
            // Transacción de fragmentos para cargar el FirstFragment en el contenedor
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, firstFragment)
                .commitNow() // Ejecución inmediata y sincrónica de la transacción
        }
    }
}


/**
 * commitNow() vs commit():
 *
 *  Mientras que commit() es asíncrono (se agenda para después),
 *  commitNow() obliga a que el fragmento se infle de inmediato.
 *
 *  En este caso es útil porque aseguras que el fragmento esté
 *  listo antes de cualquier otra operación en onCreate.
 *
 * */