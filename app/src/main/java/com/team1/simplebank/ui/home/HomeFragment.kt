package com.team1.simplebank.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.synrgy.xdomain.model.Menu
import com.team1.simplebank.R
import com.team1.simplebank.adapter.MenuBerandaAdapter
import com.team1.simplebank.databinding.FragmentHomeBinding
import com.team1.simplebank.viewmodel.HomeActivityViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeActivityViewModel by activityViewModels<HomeActivityViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        return FragmentHomeBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardViewBeranda: CardView = view.findViewById(R.id.cardview_beranda)
        cardViewBeranda.setOnClickListener {
            Toast.makeText(requireContext(), "work", Toast.LENGTH_SHORT).show()
        }
        showRecyclerView()
    }

    private fun showRecyclerView() {
        binding.rvMenu.setHasFixedSize(true)
        binding.rvMenu.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.rvMenu.animation = null
        viewModel.addDataRecyclerView(resources)

        viewModel.listData.observe(viewLifecycleOwner) { data ->
            val adapter = MenuBerandaAdapter(data) {
                clicked(it)
            }
            binding.rvMenu.adapter = adapter
        }

        binding.btnShowLess2.setOnClickListener {

        }

    }

    private fun clicked(idMenu: Menu) {
        when (idMenu.id) {
            1 -> findNavController().navigate(R.id.action_navigation_home_to_cashWithDrawlFragment)
            2 -> Toast.makeText(requireContext(), R.string.cash_deposit_title, Toast.LENGTH_SHORT)
                .show()

            3 -> Toast.makeText(requireContext(), R.string.transfer_title, Toast.LENGTH_SHORT)
                .show()

            4 -> findNavController().navigate(R.id.action_navigation_home_to_virtualAccountFragment)
            5 -> Toast.makeText(requireContext(), R.string.gold_tube_title, Toast.LENGTH_SHORT)
                .show()

            6 -> Toast.makeText(requireContext(), R.string.pay_title, Toast.LENGTH_SHORT).show()
            7 -> findNavController().navigate(R.id.action_navigation_home_to_topUpEWalletFragment)
            8 -> Toast.makeText(requireContext(), R.string.etc_title, Toast.LENGTH_SHORT)
                .show()

        }
    }


}