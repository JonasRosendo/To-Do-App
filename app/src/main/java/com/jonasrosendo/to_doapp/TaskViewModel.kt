package com.jonasrosendo.to_doapp

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonasrosendo.to_doapp.database.TaskDatabase
import com.jonasrosendo.to_doapp.data.model.Task
import com.jonasrosendo.to_doapp.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val HIGH = 0
private const val MEDIUM = 1
private const val LOW = 2
private const val FIRST_ITEM_POSITION = 0

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository by lazy {
        TaskRepository(TaskDatabase.getDatabase(application).taskDao())
    }

    val tasks = MutableLiveData<List<Task>>()
    val showEmptyState = MutableLiveData<Boolean>()

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                HIGH -> setTextColorOnSpinnerItem(R.color.red, parent)
                MEDIUM -> setTextColorOnSpinnerItem(R.color.yellow, parent)
                LOW -> setTextColorOnSpinnerItem(R.color.green, parent)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    fun getAllTasks() {
        viewModelScope.launch(Dispatchers.Main) {
            tasks.value = repository.getAllTasks()
            isTasksEmpty()
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
            getAllTasks()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(task)
            getAllTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
            getAllTasks()
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.deleteAllTasks()
            getAllTasks()
        }
    }

    fun searchTaskByTitle(title: String) {
        viewModelScope.launch(Dispatchers.Main) {
            tasks.postValue(repository.searchTaskByTitle(title))
        }
    }

    fun sortByHighPriority() {
        viewModelScope.launch(Dispatchers.IO) {
            tasks.postValue(repository.sortByHighPriority())
        }
    }

    fun sortByMediumPriority() {
        viewModelScope.launch(Dispatchers.IO) {
            tasks.postValue(repository.sortByMediumPriority())
        }
    }

    fun sortByLowPriority() {
        viewModelScope.launch(Dispatchers.IO) {
            tasks.postValue(repository.sortByLowPriority())
        }
    }

    private fun setTextColorOnSpinnerItem(colorId: Int, parent: AdapterView<*>?) {
        (parent?.getChildAt(FIRST_ITEM_POSITION) as TextView).setTextColor(
            ContextCompat.getColor(
                getApplication(),
                colorId
            )
        )
    }

    private fun isTasksEmpty() {
        showEmptyState.value = tasks.value.isNullOrEmpty()
    }
}