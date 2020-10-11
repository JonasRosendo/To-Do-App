package com.jonasrosendo.to_doapp.data.model

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.jonasrosendo.to_doapp.R
import kotlinx.android.synthetic.main.item_task_adapter.view.*

private const val FIRST_POSITION = 0
private const val SECOND_POSITION = 1
private const val THIRD_POSITION = 2

enum class Priority {
    HIGH,
    MEDIUM,
    LOW;

    companion object {

        fun parsePriority(context: Context, priority: String): Priority {
            return when (priority) {
                context.getString(R.string.priority_high) -> HIGH
                context.getString(R.string.priority_medium) -> MEDIUM
                context.getString(R.string.priority_low) -> LOW
                else -> LOW
            }
        }

        fun getPriorityPosition(priority: Priority): Int {
            return when (priority) {
                HIGH -> FIRST_POSITION
                MEDIUM -> SECOND_POSITION
                LOW -> THIRD_POSITION
            }
        }

        fun setPriorityBackgroundColor(view: View, priority: Priority) {
            when (priority) {
                HIGH -> setPriorityColor(view, R.color.red)
                MEDIUM -> setPriorityColor(view, R.color.yellow)
                LOW -> setPriorityColor(view, R.color.green)
            }
        }

        private fun setPriorityColor(view: View, colorId: Int) {
            view.cv_priority_indicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    colorId
                )
            )
        }
    }
}