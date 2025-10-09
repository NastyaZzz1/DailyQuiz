package com.nastya.dailyquiz.ui.history

import androidx.recyclerview.widget.DiffUtil

class HistoryDiffItemCallback : DiffUtil.ItemCallback<HistoryViewModel.HistoryModel>() {
    override fun areItemsTheSame(oldItem: HistoryViewModel.HistoryModel, newItem: HistoryViewModel.HistoryModel)
            = (oldItem.history.quizId == newItem.history.quizId)

    override fun areContentsTheSame(oldItem: HistoryViewModel.HistoryModel, newItem: HistoryViewModel.HistoryModel) = (oldItem == newItem)
}