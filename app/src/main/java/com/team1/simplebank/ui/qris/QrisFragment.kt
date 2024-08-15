package com.team1.simplebank.ui.qris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.team1.simplebank.databinding.FragmentQrisBinding
import com.team1.simplebank.navigations.QrisFeatureNavigation

class QrisFragment : Fragment() {

    private var _binding: FragmentQrisBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrisBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val qrisComposeView = binding.composeView
        qrisComposeView.setContent {
            QrisFeatureNavigation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}