package com.example.listacompras.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.listacompras.R

class PersonalizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalization)

        val fontSizes = resources.getStringArray(R.array.filter_cart)
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, fontSizes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = findViewById<Spinner>(R.id.spn_filter_cart)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedFontSize = fontSizes[position]
                // Faça algo com o tamanho da fonte selecionado, como salvar nas preferências.
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Tratamento quando nada for selecionado (opcional).
            }
        }

    }
}