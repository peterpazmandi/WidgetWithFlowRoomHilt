package com.inspirecoding.roombasics.repository

import androidx.lifecycle.LiveData
import com.inspirecoding.roombasics.model.ToDo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepository @Inject constructor (private val toDoDao: ToDoDao)
{
    val allToDo: LiveData<List<ToDo>> = toDoDao.getAllToDo()

    val getFirstToDoItem : Flow<ToDo> = toDoDao.getFirstToDoItem()

    suspend fun insertToDo(toDo: ToDo)
    {
        toDoDao.insertToDo(toDo)
    }
    suspend fun updateToDo(toDo: ToDo)
    {
        toDoDao.updateToDo(toDo)
    }
    suspend fun deleteToDo(toDo: ToDo)
    {
        toDoDao.deleteToDo(toDo)
    }
}