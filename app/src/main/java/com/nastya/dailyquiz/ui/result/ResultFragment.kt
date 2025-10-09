package com.nastya.dailyquiz.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.nastya.dailyquiz.data.local.database.HistoryDatabase
import com.nastya.dailyquiz.R
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
        val dao = HistoryDatabase.Companion.getInstance(application).historyDao

        val viewModelFactory = ResultViewModelFactory(this, dao, arguments)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        )[ResultViewModel::class.java]
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
        binding.category.text = "Категория: ${viewModel.questions[0].category}"
        binding.difficulty.text = "Сложность: ${viewModel.questions[0].difficulty}"

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