package com.team1.simplebank.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.team1.simplebank.R

class AccountSettingFragment : Fragment() {

    private lateinit var settingAdapter: SettingAdapter
    private lateinit var titleList: List<String>
    private lateinit var dataList: HashMap<String, List<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_account_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        val settingExpandable = view.findViewById<ExpandableListView>(R.id.setting_expandable)
        settingAdapter = SettingAdapter(view.context, titleList, dataList)
        settingExpandable.setAdapter(settingAdapter)
    }

    private fun initData() {
        titleList = listOf("Atur Limit Kartu", "Informasi", "Pusat Bantuan", "Keamanan")
        dataList = HashMap()

        val aturLimitKartu = listOf("Limit Transfer", "Limit Tarik Tunai", "Limit Pinjaman", "Limit Top Up")
        val informasi = listOf("Informasi Simple pinjam", "Informasi Simpan dollar", "Informasi Update Kartu")
        val pusatBantuan = listOf("Bantuan via Email", "Bantuan via Whasapp")
        val keamanan = listOf("Ganti PIN sandi", "Ganti Fingerprint")

        dataList[titleList[0]] = aturLimitKartu
        dataList[titleList[1]] = informasi
        dataList[titleList[2]] = pusatBantuan
        dataList[titleList[3]] = keamanan
    }

}