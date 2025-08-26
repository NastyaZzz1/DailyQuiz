package com.nastya.dailyquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nastya.dailyquiz.HistoryViewModel.HistoryState
import com.nastya.dailyquiz.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = HistoryDatabase.getInstance(application).historyDao
        val viewModelFactory = HistoryViewModelFactory(dao, requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HistoryItemAdapter (
            clickLongListener = { quizId ->
                viewModel.onQuizLongClicked(quizId)
            },
            clickDeleteListener = { quizId ->
                viewModel.onDeleteClicked(quizId, requireContext())
            }
        )

        binding.historyList.adapter = adapter
        viewModel.quizzes.observe(viewLifecycleOwner, Observer {
            if (it?.any { quiz ->
                quiz.state == HistoryState.NOT_SELECTED
            } ?: false) {
                binding.historyTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.overlay))
            } else {
                binding.historyTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }

            it?.let {
                adapter.submitList(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}