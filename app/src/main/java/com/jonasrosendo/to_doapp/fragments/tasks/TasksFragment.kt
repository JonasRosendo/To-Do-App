package com.jonasrosendo.to_doapp.fragments.tasks

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jonasrosendo.to_doapp.R
import com.jonasrosendo.to_doapp.TaskUtils.confirmItemRemoval
import com.jonasrosendo.to_doapp.TaskUtils.hideKeyboard
import com.jonasrosendo.to_doapp.TaskViewModel
import com.jonasrosendo.to_doapp.data.model.Task
import com.jonasrosendo.to_doapp.databinding.FragmentTasksBinding
import com.jonasrosendo.to_doapp.fragments.tasks.adapter.SwipeToDelete
import com.jonasrosendo.to_doapp.fragments.tasks.adapter.TaskAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_tasks.view.*

class TasksFragment : Fragment(), SearchView.OnQueryTextListener {

    private val taskAdapter: TaskAdapter by lazy { TaskAdapter(arrayListOf()) }
    private val viewModel: TaskViewModel by viewModels()
    private var binding: FragmentTasksBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        hideKeyboard(requireActivity())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupTasksList()
        viewModel.getAllTasks()
        setHasOptionsMenu(true)
        observeViewModel()
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedTask = taskAdapter.tasks[viewHolder.adapterPosition]

                viewModel.deleteTask(deletedTask)

                restoreDeletedTask(
                    view = viewHolder.itemView,
                    deletedTask = deletedTask
                )
            }
        }

        val touchHelper = ItemTouchHelper(swipeCallback)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedTask(view: View, deletedTask: Task) {
        Snackbar.make(view, "Deleted: ${deletedTask.title}", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                viewModel.insertTask(task = deletedTask)
            }.show()
    }

    private fun setupTasksList() {
        binding?.root?.rv_tasks?.apply {
            adapter = taskAdapter
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
            swipeToDelete(this)
        }
    }

    private fun observeViewModel() {
        viewModel.tasks.observe(viewLifecycleOwner, {
            taskAdapter.update(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu)
        val searchItem = menu.findItem(R.id.tasks_menu_search)
        val searchView = searchItem.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.tasks_menu_delete_all -> confirmItemRemoval(
                requireContext(),
                title = getString(R.string.warning),
                message = getString(R.string.delete_all_tasks_message),
                onPositiveClick = { deleteAllTasks() }
            )

            R.id.tasks_menu_priority_high -> viewModel.sortByHighPriority()
            R.id.tasks_menu_priority_medium -> viewModel.sortByMediumPriority()
            R.id.tasks_menu_priority_low -> viewModel.sortByLowPriority()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllTasks() {
        viewModel.deleteAllTasks()
    }

    override fun onQueryTextSubmit(queryText: String?): Boolean {
        return textSubmitted(queryText)
    }

    override fun onQueryTextChange(queryText: String?): Boolean {
        return textSubmitted(queryText)
    }

    private fun textSubmitted(queryText: String?): Boolean {
        queryText?.let {
            searchTaskByTitle(it)
            return true
        }

        return false
    }

    private fun searchTaskByTitle(query: String) {
        viewModel.searchTaskByTitle("%$query%")
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}