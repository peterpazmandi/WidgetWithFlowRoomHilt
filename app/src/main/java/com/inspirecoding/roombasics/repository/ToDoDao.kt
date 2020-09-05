package com.inspirecoding.roombasics.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.inspirecoding.roombasics.model.ToDo
import kotlinx.coroutines.flow.Flow


@Dao
interface ToDoDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo (toDo: ToDo)

    @Update
    suspend fun updateToDo (toDo: ToDo)

    @Delete
    suspend fun deleteToDo (toDo: ToDo)

    @Query("SELECT * FROM ToDo")
    fun getAllToDo(): LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo WHERE toDoId = 1")
    fun getFirstToDoItem() : Flow<ToDo>
}