package com.example.listacompras.presentation

object LanguageManager {

    var currentLanguage: String = "pt" // Padrão para português

    fun setLanguage(language: String) {
        currentLanguage = language
        // Lógica para aplicar o idioma em toda a aplicação, se necessário
    }
}
