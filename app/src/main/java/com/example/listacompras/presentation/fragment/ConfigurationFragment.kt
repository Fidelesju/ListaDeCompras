package com.example.listacompras.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.ConfigurationItem
import com.example.listacompras.presentation.activity.AppearanceActivity
import com.example.listacompras.presentation.activity.HelpActivity
import com.example.listacompras.presentation.activity.IdiomaActivity
import com.example.listacompras.presentation.activity.PersonalizationActivity
import com.example.listacompras.presentation.adapter.ConfigurationAdapter

class ConfigurationFragment : Fragment() {

    private lateinit var configurationArray: Array<String>
    private lateinit var configurationItems: List<ConfigurationItem>

    private val adapter: ConfigurationAdapter by lazy {
        ConfigurationAdapter(configurationItems, ::openActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Agora é um bom momento para acessar o contexto
        configurationArray = resources.getStringArray(R.array.configuration)
        configurationItems = configurationArray.map {
            ConfigurationItem(it)
        }

        return inflater.inflate(R.layout.fragment_configuration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvConfiguration: RecyclerView = view.findViewById(R.id.rv_configuration)
        setupRecyclerView(rvConfiguration)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        // Atualize o adaptador com a nova lista de itens de configuração
        adapter.submitList(configurationItems)
        recyclerView.adapter = adapter
    }

    private fun openActivity(configurationItem: ConfigurationItem) {
        when (configurationItem.title) {
            "Aparência" -> {
                val intent = Intent(requireContext(), AppearanceActivity::class.java)
                startActivity(intent)
            }

            "Idioma" -> {
                val intent = Intent(requireContext(), IdiomaActivity::class.java)
                startActivity(intent)
            }

            "Personalização" -> {
                val intent = Intent(requireContext(), PersonalizationActivity::class.java)
                startActivity(intent)
            }

            "Ajuda" -> {
                val intent = Intent(requireContext(), HelpActivity::class.java)
                startActivity(intent)
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = ConfigurationFragment()
    }
}

