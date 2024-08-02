package com.team1.simplebank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.xdomain.model.MutationDataUI
import com.team1.simplebank.R
import com.team1.simplebank.databinding.LayoutHeaderRecyclerviewBinding
import com.team1.simplebank.databinding.LayoutItemRecyclerviewBinding

class MutationPagerAdapter :
    PagingDataAdapter<MutationDataUI, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {

        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MutationDataUI>() {

            //perlu id yang unik untuk menampilkan datanya ini nanti okeh
            override fun areItemsTheSame(
                oldItem: MutationDataUI,
                newItem: MutationDataUI
            ): Boolean {
                return when {
                    oldItem is MutationDataUI.Header && newItem is MutationDataUI.Header -> {
                        oldItem.date == newItem.date
                    }

                    oldItem is MutationDataUI.Item && newItem is MutationDataUI.Item -> {
                        oldItem.recipientTargetAccount == newItem.recipientTargetAccount
                    }

                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: MutationDataUI,
                newItem: MutationDataUI
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MutationDataUI.Header -> ITEM_VIEW_TYPE_HEADER
            is MutationDataUI.Item -> ITEM_VIEW_TYPE_ITEM
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
            is MutationDataUI.Header -> {
                (holder as HeaderViewHolder).bind(item)
            }

            is MutationDataUI.Item -> {
                (holder as ItemViewHolder).bind(item)
            }

            else -> {}
        }
    }


    //untuk dataclass menggunakan sealed class yang memiliki 2 dataclass
    inner class HeaderViewHolder(private val binding: LayoutHeaderRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: MutationDataUI.Header) {
            binding.headersTitle.text = header.date
        }
    }

    inner class ItemViewHolder(private val binding: LayoutItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MutationDataUI.Item) {
            binding.transferDetail.text = item.transactionType
            binding.transferDetail.text = item.recipientName
            binding.amountExpenseOrIncome.text = item.amount.toString()
            val mutationType = item.mutationType
            if (mutationType == "PENGELUARAN") {
                binding.iconItem.setImageResource(R.drawable.expense_icon_mutation)
            } else if (mutationType == "PEMASUKAN") {
                binding.iconItem.setImageResource(R.drawable.income_icon_mutation)
            }
        }
    }
}