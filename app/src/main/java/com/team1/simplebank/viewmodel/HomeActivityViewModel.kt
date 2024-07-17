package com.team1.simplebank.viewmodel

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.synrgy.xdomain.model.Menu
import com.team1.simplebank.R

class HomeActivityViewModel : ViewModel() {
    private val _listDataItem = MutableLiveData<List<Menu>>()
    val listData: LiveData<List<Menu>> = _listDataItem

    private fun listItemMenu(resources: Resources): List<Menu> {
        return listOf(
            Menu(1, R.drawable.icon_tarik_tunai, resources.getString(R.string.cash_withdrawal_title)),
            Menu(2, R.drawable.icon_deposit2, resources.getString(R.string.cash_deposit_title)),
            Menu(3, R.drawable.icon_transfer, resources.getString(R.string.transfer_title)),
            Menu(4, R.drawable.icon_virtual_account,resources.getString(R.string.virtual_account_title)),
            Menu(5, R.drawable.icon_tarik_tunai, resources.getString(R.string.gold_tube_title)),
            Menu(6, R.drawable.icon_pay, resources.getString(R.string.pay_title)),
            Menu(7, R.drawable.icon_top_up_e_wallet, resources.getString(R.string.top_up_e_wallet_title)),
            Menu(8, R.drawable.icon_etc, resources.getString(R.string.etc_title)),
        )
    }

    fun addDataRecyclerView(resources: Resources) {
        _listDataItem.value = listItemMenu(resources)
    }


}