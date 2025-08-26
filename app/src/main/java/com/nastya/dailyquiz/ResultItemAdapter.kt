package com.nastya.dailyquiz

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.nastya.dailyquiz.databinding.ResultItemBinding

class ResultItemAdapter(private val items: List<QuestionParse>, val context: Context):
    RecyclerView.Adapter<ResultItemAdapter.ResultItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItemViewHolder {
        return ResultItemViewHolder.inflateFrom(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ResultItemViewHolder, position: Int) {
        holder.bind(
            items[position],
            position,
            items.count(),
            context)
    }

    class ResultItemViewHolder(val binding: ResultItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): ResultItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ResultItemBinding.inflate(layoutInflater, parent, false)
                return ResultItemViewHolder(binding)
            }
        }

        fun bind(item: QuestionParse,
                 position: Int,
                 countQuestion: Int,
                 context: Context) {
            binding.questionCount.text = "Вопрос ${position+1} из ${countQuestion}"
            binding.markerAnswer.setImageResource(
                if (item.correctAnswer == item.chooseAnswer) R.drawable.right_radio_icon
                else R.drawable.wrong_radio_icon
            )
            binding.questionTitle.text = item.question

            binding.radioGroup.removeAllViews()

            item.allAnswers.forEachIndexed { index, answerText ->
                val isCorrect = item.chooseAnswer == answerText && item.chooseAnswer == item.correctAnswer
                val isWrong = item.chooseAnswer == answerText && item.chooseAnswer != item.correctAnswer
                val styleRes = when {
                    isCorrect -> R.style.RadioBtnStyleRight
                    isWrong -> R.style.RadioBtnStyleWrong
                    else -> R.style.RadioBtnStyleInset
                }

                val radioButton = RadioButton(context,null, 0, styleRes).apply {
                    id = View.generateViewId()
                    this.text = HtmlCompat.fromHtml(answerText, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    isClickable = false
                    isFocusable = false
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
}