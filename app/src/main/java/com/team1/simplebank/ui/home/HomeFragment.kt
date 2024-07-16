package com.team1.simplebank.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.team1.simplebank.R
import com.team1.simplebank.adapter.MenuBerandaAdapter
import com.team1.simplebank.databinding.FragmentHomeBinding
import com.team1.simplebank.domain.model.dataclass.Menu
import com.team1.simplebank.viewmodel.HomeActivityViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeActivityViewModel by activityViewModels<HomeActivityViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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


    }

    private fun clicked(idMenu: Menu) {
        when (idMenu.id) {
            1 -> Toast.makeText(requireContext(), "tarik tunai", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(requireContext(), "selain tarik tunai", Toast.LENGTH_SHORT)
                .show()

        }
    }


}