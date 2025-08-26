package com.nastya.dailyquiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.nastya.dailyquiz.databinding.FragmentQuestionBinding
import androidx.navigation.findNavController
import android.content.res.Resources
import com.google.gson.Gson

class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener{
            view.findNavController()
                .navigate(R.id.action_questionFragment_to_startFragment2)
        }

        val countQuestions = viewModel.questions.count()
        setTextView()

        binding.continueQuizBtn.setOnClickListener {
            viewModel.increaseIndexQuests()
            val indexQuestions = viewModel.indexQuestions.value!!

            if(indexQuestions < countQuestions) {
                if(indexQuestions == countQuestions-1) {
                    binding.continueQuizBtn.text = "Завершить"
                }
                setTextView()
                viewModel.setAnswerSelected(false)
            }
            else if(indexQuestions == countQuestions) {
                val action = QuestionFragmentDirections
                    .actionQuestionFragmentToResultFragment(Gson().toJson(viewModel.questions))
                view.findNavController().navigate(action)
            }
        }

        viewModel.isAnswerSelected.observe(viewLifecycleOwner) { isSelected ->
            binding.continueQuizBtn.isEnabled = isSelected
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            viewModel.setAnswerSelected(checkedId != -1)
            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
            val selectAnswer = selectedRadioButton.text.toString()
            viewModel.setChooseAnswer(selectAnswer)
        }
    }

    fun setTextView() {
        binding.questionCount.text = "Вопрос ${viewModel.indexQuestions.value!!+1} из ${viewModel.questions.count()}"
        binding.questionTitle.text = viewModel.getQuestionTitle()

        binding.radioGroup.removeAllViews()

        Log.d("Frag", "answersCorrect: ${viewModel.getCorrectAnswer()}")

        viewModel.getAnswers().forEachIndexed { index, answerText ->
            val radioButton = RadioButton(context,null, 0, R.style.RadioBtnStyleInset).apply {
                id = View.generateViewId()
                this.text = HtmlCompat.fromHtml(answerText, HtmlCompat.FROM_HTML_MODE_LEGACY)
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 15.dpToPx())
                }
            }
            binding.radioGroup.addView(radioButton)
        }
    }
    fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}