package com.jonasrosendo.to_doapp.fragments.update_task

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jonasrosendo.to_doapp.R
import com.jonasrosendo.to_doapp.TaskUtils
import com.jonasrosendo.to_doapp.TaskUtils.confirmItemRemoval
import com.jonasrosendo.to_doapp.TaskViewModel
import com.jonasrosendo.to_doapp.data.model.Priority
import com.jonasrosendo.to_doapp.data.model.Task
import com.jonasrosendo.to_doapp.databinding.FragmentUpdateBinding
import kotlinx.android.synthetic.main.fragment_update.*

class UpdateTaskFragment : Fragment() {

    private val args by navArgs<UpdateTaskFragmentArgs>()
    private val viewModel: TaskViewModel by viewModels()
    private var binding: FragmentUpdateBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        binding?.args = args
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.sprTaskUpdatePriority?.onItemSelectedListener = viewModel.listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update_task_menu -> updateTask()
            R.id.update_task_delete_menu -> confirmItemRemoval(
                context = requireContext(),
                title = args.currentItem.title,
                message = getString(R.string.delete_single_task_message, args.currentItem.title),
                onPositiveClick = { deleteTask() }
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTask() {
        viewModel.deleteTask(args.currentItem)
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    private fun updateTask() {
        val title = et_update_title.text.toString().trim()
        val description = et_task_update_description.text.toString().trim()
        val priority = Priority.parsePriority(
            et_update_title.context,
            spr_task_update_priority.selectedItem.toString()
        )

        if (TaskUtils.allTextsFilled(title, description)) {
            viewModel.updateTask(Task(args.currentItem.id, title, priority, description))
            TaskUtils.showFeedbackMessage(et_update_title.context, "Task successfully updated!")
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            TaskUtils.showFeedbackMessage(et_update_title.context, "Fill out form fields!")
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}