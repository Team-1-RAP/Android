package com.team1.simplebank.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team1.simplebank.databinding.ItemButtonMenuBinding
import com.team1.simplebank.domain.model.dataclass.Menu

class MenuBerandaAdapter(val listItem: List<Menu>, val clickListener: (Menu) -> Unit) :
    RecyclerView.Adapter<MenuBerandaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemButtonMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Menu) {
            binding.iconItem.setImageResource(data.image)
            binding.textIcon.text = data.title
            binding.root.setOnClickListener {
                clickListener(data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemButtonMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        holder.itemView.foreground = null
        holder.bind(item)
    }

}