package com.example.listacompras.presentation.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.listacompras.R
import com.example.listacompras.presentation.LanguageManager
import java.util.Locale

class IdiomaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idioma)
        val btnPortuguese: TextView = findViewById(R.id.tv_portugues)
        val btnEnglish: TextView = findViewById(R.id.tv_ingles)

        btnPortuguese.setOnClickListener {
            LanguageManager.setLanguage("pt")
            selectLanguage("pt")
        }

        btnEnglish.setOnClickListener {
            LanguageManager.setLanguage("en")
            selectLanguage("en")
        }
    }

    private fun selectLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        // Reinicie a atividade principal para aplicar as mudanças
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        finish()
        startActivity(intent)
    }
}
