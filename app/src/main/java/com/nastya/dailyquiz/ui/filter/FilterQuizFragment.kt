package com.nastya.dailyquiz.ui.filter

import android.os.Bundle
import com.nastya.dailyquiz.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.nastya.dailyquiz.databinding.FragmentFilterQuizBinding

class FilterQuizFragment : Fragment() {
    private var _binding: FragmentFilterQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FilterQuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterQuizBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[FilterQuizViewModel::class.java]
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategorySpinner()
        setupDifficultySpinner()

        binding.continueQuizBtn.setOnClickListener {
            if(viewModel.isFormValid.value == true) {
                navigateToNextScreen()
            }
        }

        viewModel.isFormValid.observe(viewLifecycleOwner) { isSelected ->
            binding.continueQuizBtn.isEnabled = isSelected
        }
    }

    private fun navigateToNextScreen() {
        val selectedCategoryId = viewModel.selectedCategory.value ?: return
        val selectedDifficulty = viewModel.selectedDifficulty.value ?: return

        val action = FilterQuizFragmentDirections
            .actionFilterQuizFragmentToLoadFragment(
                selectedCategoryId,
                selectedDifficulty
            )
        requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
    }

    private fun setupCategorySpinner() {
        val adapterCategory = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            viewModel.categories.map{ it.name}
        )

        adapterCategory.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.filledExposedCategory.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.spinner_dropdown_background)
        )
        binding.filledExposedCategory.setAdapter(adapterCategory)

        binding.filledExposedCategory.onItemClickListener =  AdapterView.OnItemClickListener { parent, view, position, id ->
            viewModel.selectedCategory.value = viewModel.categories[position].id
        }
    }

    private fun setupDifficultySpinner() {
        val adapterDifficulty: ArrayAdapter<*> =
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.difficulty,
                R.layout.spinner_item
            )

        adapterDifficulty.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.filledExposedDifficulty.setAdapter(adapterDifficulty)

        binding.filledExposedDifficulty.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.spinner_dropdown_background)
        )
        binding.filledExposedDifficulty.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
            viewModel.selectedDifficulty.value = parent?.getItemAtPosition(position).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}