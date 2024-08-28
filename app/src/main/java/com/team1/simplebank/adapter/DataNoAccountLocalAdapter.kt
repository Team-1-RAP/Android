package com.team1.simplebank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.xdomain.model.DataUserDestinationLocalModel
import com.team1.simplebank.common.utils.splitWordIntoOneLetter
import com.team1.simplebank.databinding.LayoutItemUserTransferBinding

class DataNoAccountLocalAdapter(val itemClicked:(DataUserDestinationLocalModel)->Unit, val btnDeleteClicked:(String)->Unit):ListAdapter<DataUserDestinationLocalModel, DataNoAccountLocalAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding:LayoutItemUserTransferBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:DataUserDestinationLocalModel){
            with(binding){
                iconName.text = splitWordIntoOneLetter(data.fullName)
                transferAccountUsername.text = data.fullName
                btnDeleteTransferAccount.setOnClickListener {
                    btnDeleteClicked(data.noAccount)
                }
                root.setOnClickListener {
                    itemClicked(data)
                }
            }
        }
    }
    companion object{
        val DIFF_CALLBACK : DiffUtil.ItemCallback<DataUserDestinationLocalModel> =
            object : DiffUtil.ItemCallback<DataUserDestinationLocalModel>() {
                override fun areItemsTheSame(
                    oldItem: DataUserDestinationLocalModel,
                    newItem: DataUserDestinationLocalModel
                ): Boolean {
                    return oldItem.noAccount == newItem.noAccount
                }

                override fun areContentsTheSame(
                    oldItem: DataUserDestinationLocalModel,
                    newItem: DataUserDestinationLocalModel
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemUserTransferBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataNoAccount = getItem(position)
        holder.bind(dataNoAccount)
    }
}