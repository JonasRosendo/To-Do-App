package com.jonasrosendo.to_doapp.fragments.add_task

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jonasrosendo.to_doapp.R
import com.jonasrosendo.to_doapp.TaskUtils.allTextsFilled
import com.jonasrosendo.to_doapp.TaskUtils.showFeedbackMessage
import com.jonasrosendo.to_doapp.TaskViewModel
import com.jonasrosendo.to_doapp.data.model.Priority
import com.jonasrosendo.to_doapp.data.model.Task
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddTaskFragment : Fragment() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        view.spr_task_priority.onItemSelectedListener = taskViewModel.listener
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.add_task_menu -> insertTaskToDatabase()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertTaskToDatabase() {
        val title = et_add_title.text.toString().trim()
        val priority = spr_task_priority.selectedItem.toString()
        val description = et_task_description.text.toString().trim()

        if (allTextsFilled(title = title, description = description)) {
            taskViewModel.insertTask(
                Task(
                    0,
                    title,
                    Priority.parsePriority(et_add_title.context, priority),
                    description
                )
            )

            showFeedbackMessage(requireContext(), "Task successfully saved :)")
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            showFeedbackMessage(requireContext(), "Could not save task! :(")
        }
    }
}