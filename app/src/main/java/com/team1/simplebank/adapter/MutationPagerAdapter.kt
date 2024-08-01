package com.team1.simplebank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.xdomain.model.ItemPagingDataUI
import com.team1.simplebank.R
import com.team1.simplebank.common.handler.ListItemMutationPaging
import com.team1.simplebank.databinding.LayoutHeaderRecyclerviewBinding
import com.team1.simplebank.databinding.LayoutItemRecyclerviewBinding

class MutationPagerAdapter() :
    PagingDataAdapter<ListItemMutationPaging, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {

        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItemMutationPaging>() {

            //perlu id yang unik untuk menampilkan datanya ini nanti okeh
            override fun areItemsTheSame(
                oldItem: ListItemMutationPaging,
                newItem: ListItemMutationPaging
            ): Boolean {
                return when {
                    oldItem is ListItemMutationPaging.Header && newItem is ListItemMutationPaging.Header -> {
                        oldItem.title == newItem.title
                    }

                    oldItem is ListItemMutationPaging.Item && newItem is ListItemMutationPaging.Item -> {
                        oldItem.data.date == newItem.data.date
                    }

                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: ListItemMutationPaging,
                newItem: ListItemMutationPaging
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItemMutationPaging.Header -> ITEM_VIEW_TYPE_HEADER
            is ListItemMutationPaging.Item -> ITEM_VIEW_TYPE_ITEM
            else -> throw UnsupportedOperationException("Unknown View")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                val binding = LayoutHeaderRecyclerviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(binding)
            }

            ITEM_VIEW_TYPE_ITEM -> {
                val binding = LayoutItemRecyclerviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ItemViewHolder(binding)
            }

            else -> throw UnsupportedOperationException("INVALID TYPE")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ListItemMutationPaging.Header -> {
                (holder as HeaderViewHolder).bind(item)
            }

            is ListItemMutationPaging.Item -> {
                (holder as ItemViewHolder).bind(item)
            }

            else -> {}
        }
    }


    //untuk dataclass menggunakan sealed class yang memiliki 2 dataclass
    inner class HeaderViewHolder(val binding: LayoutHeaderRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: ListItemMutationPaging.Header) {
            binding.headersTitle.text = header.title
        }
    }

    inner class ItemViewHolder(val binding: LayoutItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItemMutationPaging.Item) {
            binding.transferDetail.text = item.data.recipientName
            binding.amountExpenseOrIncome.text = item.data.amount.toString()
            val mutationType = item.data.mutationType
            if (mutationType == "PENGELUARAN") {
                binding.iconItem.setImageResource(R.drawable.expense_icon_mutation)
            } else if (mutationType == "PEMASUKAN") {
                binding.iconItem.setImageResource(R.drawable.income_icon_mutation)
            }
        }
    }
}