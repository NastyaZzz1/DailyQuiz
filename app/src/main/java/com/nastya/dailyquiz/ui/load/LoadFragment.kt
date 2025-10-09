package com.nastya.dailyquiz.ui.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.nastya.dailyquiz.databinding.FragmentLoadBinding

class LoadFragment : Fragment() {
    private var _binding: FragmentLoadBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[LoadViewModel::class.java]
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getQuestions()

        viewModel.questions.observe(viewLifecycleOwner) { questions ->
            questions?.let {
                val action = LoadFragmentDirections
                    .actionLoadFragmentToQuestionFragment(Gson().toJson(questions))
                view.findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}