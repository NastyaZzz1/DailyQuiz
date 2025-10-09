package com.nastya.dailyquiz.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.nastya.dailyquiz.R
import com.nastya.dailyquiz.databinding.FragmentBlankHistoryBinding

class BlankHistoryFragment : Fragment() {
    private var _binding: FragmentBlankHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlankHistoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.restartQuizBtn.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_blankHistoryFragment_to_startFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}