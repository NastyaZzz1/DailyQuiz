package com.nastya.dailyquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.nastya.dailyquiz.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = HistoryDatabase.getInstance(application).historyDao

        val viewModelFactory = ResultViewModelFactory(this, dao, arguments)
        viewModel = ViewModelProvider(
            this, viewModelFactory)[ResultViewModel::class.java]
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ResultItemAdapter(viewModel.questions, requireContext())
        viewModel.addHistory()

        binding.resultList.adapter = adapter
        binding.restartQuizBtn.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_resultFragment_to_startFragment)
        }

        binding.restartQuizBtn2.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_resultFragment_to_startFragment)
        }

        setTextView()
    }

    fun setTextView() {
        val countCorrectQuestions = viewModel.countCorrectQuestions
        binding.countRightAnswers.text = "${countCorrectQuestions} из ${viewModel.countQuestions}"
        binding.resHeading.text = viewModel.getResHeading(requireContext())
        binding.resSubtitle.text = viewModel.getResSubtitle(requireContext())

        val stars = listOf(
            binding.star1,
            binding.star2,
            binding.star3,
            binding.star4,
            binding.star5
        )

        for(i in 0..4) {
            stars[i].setImageResource(
                if (i < countCorrectQuestions) R.drawable.star_right_icon
                else R.drawable.star_wrong_icon
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}