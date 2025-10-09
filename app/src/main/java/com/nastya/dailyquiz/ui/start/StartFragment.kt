package com.nastya.dailyquiz.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.nastya.dailyquiz.data.local.database.HistoryDatabase
import com.nastya.dailyquiz.R
import com.nastya.dailyquiz.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = HistoryDatabase.Companion.getInstance(application).historyDao
        val viewModelFactory = StartViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory)[StartViewModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startQuizBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_startFragment_to_filterQuizFragment)
        }

        binding.historyBtn.setOnClickListener {
            viewModel.quizzes.observe(viewLifecycleOwner, Observer { quizzes ->
                if (quizzes.isNullOrEmpty()) {
                    view.findNavController()
                        .navigate(R.id.action_startFragment_to_blankHistoryFragment)
                } else {
                    view.findNavController().navigate(R.id.action_startFragment_to_historyFragment)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}