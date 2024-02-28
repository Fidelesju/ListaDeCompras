package com.example.listacompras.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.ConfigurationItem
import com.example.listacompras.data.entity.Products

class ConfigurationAdapter(
    private val items: List<ConfigurationItem>,
    private val openActivity: (configurationItem: ConfigurationItem) -> Unit
) : ListAdapter<ConfigurationItem, ConfigurationViewHolder>(ConfigurationAdapter) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfigurationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_configuration, parent, false)
        return ConfigurationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConfigurationViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, openActivity)
    }

    companion object : DiffUtil.ItemCallback<ConfigurationItem>() {
        override fun areItemsTheSame(
            oldItem: ConfigurationItem,
            newItem: ConfigurationItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ConfigurationItem,
            newItem: ConfigurationItem
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}

class ConfigurationViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val tvConfigurationItem: TextView = itemView.findViewById(R.id.tv_configuration)

    fun bind(
        configurationItem: ConfigurationItem,
        openActivity: (configurationItem: ConfigurationItem) -> Unit
    ) {

        tvConfigurationItem.text = configurationItem.title

        view.setOnClickListener {
            openActivity.invoke(configurationItem)
        }
    }


}
