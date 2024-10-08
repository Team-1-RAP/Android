package com.team1.simplebank.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.xdomain.model.Menu
import com.team1.simplebank.R
import com.team1.simplebank.databinding.ItemButtonMenuBinding

class MenuBerandaAdapter(private val listItem: List<Menu>, val clickListener: (Menu) -> Unit) :
    RecyclerView.Adapter<MenuBerandaAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemButtonMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(data: Menu) {
            binding.iconItem.setImageResource(data.image)
            binding.textIcon.text = data.title

            with(binding){
                root.setOnClickListener {
                    clickListener(data)
                }
                iconItem.setOnClickListener {
                    clickListener(data)
                }
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