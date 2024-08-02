package com.synrgy.xdomain.model

sealed class MutationDataUI {
    data class Header(val date: String) : MutationDataUI(){
        override fun toString(): String {
            return date
        }
    }
    data class Item(
        val transactionType: String,
        val mutationType: String,
        val recipientName: String,
        val type: String,
        val amount: Int,
        val recipientTargetAccount: String,
        val transactionStatus: String
    ) : MutationDataUI() {
        override fun toString(): String {
            return transactionStatus + transactionType + mutationType + recipientName + type + amount + recipientTargetAccount
        }
    }

    override fun toString(): String {
        return when(this){
            is Header -> this.toString()
            is Item -> this.toString()
        }
    }

}