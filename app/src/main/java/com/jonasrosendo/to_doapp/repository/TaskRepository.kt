package com.jonasrosendo.to_doapp.repository

import com.jonasrosendo.to_doapp.database.TaskDao
import com.jonasrosendo.to_doapp.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun getAllTasks() = taskDao.getAllTasks()
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun update(task: Task) = taskDao.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    suspend fun deleteAllTasks() = taskDao.deleteAllTasks()
    suspend fun searchTaskByTitle(title: String) = taskDao.searchTaskByTitle(title)
    suspend fun sortByHighPriority() = taskDao.sortByHighPriority()
    suspend fun sortByMediumPriority() = taskDao.sortByMediumPriority()
    suspend fun sortByLowPriority() = taskDao.sortByLowPriority()
}