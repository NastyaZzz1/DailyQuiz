package com.nastya.dailyquiz.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nastya.dailyquiz.R
import com.nastya.dailyquiz.databinding.HistoryItemBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class HistoryItemAdapter(
    val clickLongListener: (quizId: Long) -> Unit,
    val clickDeleteListener: (quizId: Long) -> Unit )
    : ListAdapter<HistoryViewModel.HistoryModel, HistoryItemAdapter.HistoryItemViewHolder>(
    HistoryDiffItemCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        return HistoryItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        val item = currentList[position]

        holder.bind(
            item,
            clickLongListener,
            clickDeleteListener)
    }

    class HistoryItemViewHolder(val binding: HistoryItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): HistoryItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HistoryItemBinding.inflate(layoutInflater, parent, false)
                return HistoryItemViewHolder(binding)
            }
        }

        fun bind(
            item: HistoryViewModel.HistoryModel,
            clickLongListener: (quizId: Long) -> Unit,
            clickDeleteListener: (quizId: Long) -> Unit,
        ) {
            val dateTime = LocalDateTime.parse(item.history.quizDateTime.toString())
            binding.dataQuiz.text = DateTimeFormatter.ofPattern("d MMMM", Locale("ru")).format(dateTime)
            binding.timeQuiz.text = DateTimeFormatter.ofPattern("HH:mm").format(dateTime)
            binding.historyItemHeading.text = "Quiz ${item.history.quizId}"
            binding.category.text = "Категория: ${item.history.quizCategory}"
            binding.difficulty.text = "Сложность: ${item.history.quizDifficulty}"

            val stars = listOf(
                binding.star1,
                binding.star2,
                binding.star3,
                binding.star4,
                binding.star5
            )

            for(i in 0..4) {
                stars[i].setImageResource(
                    if (i < item.history.countCorrectQuestions) R.drawable.star_right_icon
                    else R.drawable.star_wrong_icon
                )
            }
            binding.deleteHistoryBtn.visibility = if (item.state == HistoryViewModel.HistoryState.SELECTED) View.VISIBLE else View.GONE
            binding.dimOverlay.visibility = if (item.state == HistoryViewModel.HistoryState.NOT_SELECTED) View.VISIBLE else View.INVISIBLE

            binding.historyCardView.setOnLongClickListener {
                clickLongListener(item.history.quizId)
                true
            }

            binding.btn.setOnClickListener {
                clickDeleteListener(item.history.quizId)
            }
        }
    }
}