package com.jonasrosendo.to_doapp.fragments

import android.graphics.Color
import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jonasrosendo.to_doapp.R
import com.jonasrosendo.to_doapp.data.model.Priority
import com.jonasrosendo.to_doapp.data.model.Task
import com.jonasrosendo.to_doapp.fragments.tasks.TasksFragmentDirections
import kotlinx.android.synthetic.main.fragment_update.*

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyTasks")
        @JvmStatic
        fun emptyTasks(view: View, isTasksEmpty: Boolean) {
            view.visibility = if (isTasksEmpty) View.VISIBLE else View.GONE
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            view.setSelection(Priority.getPriorityPosition(priority))
        }

        @BindingAdapter("android:priorityBackgroundColor")
        @JvmStatic
        fun priorityBackgroundColor(view: CardView, priority: Priority) {
            Priority.setPriorityBackgroundColor(view, priority)
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, task: Task) {
            view.setOnClickListener {
                val action = TasksFragmentDirections.actionListFragmentToUpdateFragment(task)
                view.findNavController().navigate(action)
            }
        }
    }
}