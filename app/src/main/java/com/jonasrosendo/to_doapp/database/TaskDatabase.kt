package com.jonasrosendo.to_doapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jonasrosendo.to_doapp.data.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(PriorityTypeConverter::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var instance: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            if (instance != null) {
                return instance as TaskDatabase
            }

            synchronized(this) {

                instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "task.db"
                    ).build()

                return instance as TaskDatabase
            }
        }
    }
}