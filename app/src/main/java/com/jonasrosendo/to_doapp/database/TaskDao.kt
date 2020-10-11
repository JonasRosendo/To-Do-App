package com.jonasrosendo.to_doapp.database

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.jonasrosendo.to_doapp.data.model.Task

private const val DELETE_ALL_TASKS_QUERY = "DELETE FROM tasks"
private const val SELECT_ALL_TASKS_QUERY = "SELECT * FROM tasks ORDER BY id ASC"
private const val SEARCH_TASKS_BY_TITLE_QUERY = "SELECT * FROM tasks WHERE title LIKE :title"
private const val ORDER_BY_HIGH_PRIORITY_QUERY =
    "SELECT * FROM tasks ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END"
private const val ORDER_BY_MEDIUM_PRIORITY_QUERY =
    "SELECT * FROM tasks ORDER BY CASE WHEN priority LIKE 'M%' THEN 1 WHEN priority LIKE 'H%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END"
private const val ORDER_BY_LOW_PRIORITY_QUERY =
    "SELECT * FROM tasks ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END"

@Dao
interface TaskDao {

    @Query(SELECT_ALL_TASKS_QUERY)
    suspend fun getAllTasks(): List<Task>

    @Insert(onConflict = IGNORE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task): Int

    @Query(DELETE_ALL_TASKS_QUERY)
    suspend fun deleteAllTasks()

    @Query(SEARCH_TASKS_BY_TITLE_QUERY)
    suspend fun searchTaskByTitle(title: String): List<Task>

    @Query(ORDER_BY_HIGH_PRIORITY_QUERY)
    suspend fun sortByHighPriority(): List<Task>

    @Query(ORDER_BY_LOW_PRIORITY_QUERY)
    suspend fun sortByLowPriority(): List<Task>

    @Query(ORDER_BY_MEDIUM_PRIORITY_QUERY)
    suspend fun sortByMediumPriority(): List<Task>
}