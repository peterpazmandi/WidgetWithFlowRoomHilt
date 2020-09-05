package com.inspirecoding.roombasics.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspirecoding.roombasics.MyApp
import com.inspirecoding.roombasics.model.ToDo
import com.inspirecoding.roombasics.repository.ToDoRepository
import com.inspirecoding.roombasics.repository.ToDoRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel: ViewModel()
{
    private val toDoRepository: ToDoRepository

    val listOfToDosLiveData: LiveData<List<ToDo>>

    init
    {
        val toDoDao = ToDoRoomDatabase.getDatabase(MyApp.applicationContext()).toDoDao()
        toDoRepository = ToDoRepository(toDoDao)
        listOfToDosLiveData = toDoRepository.allToDo
    }

    fun addToDo(toDo: ToDo)
    {
        viewModelScope.launch (Dispatchers.IO) {
            toDoRepository.insertToDo(toDo)
        }
    }
    fun updateToDo(toDo: ToDo)
    {
        viewModelScope.launch (Dispatchers.IO) {
            toDoRepository.updateToDo(toDo)
        }
    }
    fun deleteToDo(toDo: ToDo)
    {
        viewModelScope.launch (Dispatchers.IO) {
            toDoRepository.deleteToDo(toDo)
        }
    }
}