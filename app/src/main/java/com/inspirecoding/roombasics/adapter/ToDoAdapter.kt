package com.inspirecoding.roombasics.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.inspirecoding.roombasics.R
import com.inspirecoding.roombasics.databinding.ItemTodoRecyclerviewBinding
import com.inspirecoding.roombasics.enums.Prioirities
import com.inspirecoding.roombasics.fragments.RecyclerViewFragmentDirections
import com.inspirecoding.roombasics.model.ToDo

class ToDoAdapter(val context: Context): RecyclerView.Adapter<ToDoAdapter.ToDoHolder>()
{
    private var listOfToDos = emptyList<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdapter.ToDoHolder
    {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemTodoRecyclerviewBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_todo_recyclerview, parent, false
        )

        return ToDoHolder(binding)
    }

    override fun getItemCount() = listOfToDos.size

    internal fun addToDos(listOfToDos: List<ToDo>)
    {
        this.listOfToDos = listOfToDos
        notifyDataSetChanged()
    }
    internal fun getItemFromPosition(position: Int): ToDo
    {
        return listOfToDos[position]
    }

    override fun onBindViewHolder(holder: ToDoAdapter.ToDoHolder, position: Int)
    {
        holder.bindToDo(listOfToDos[position])
    }

    inner class ToDoHolder(val binding: ItemTodoRecyclerviewBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener
    {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bindToDo(toDo: ToDo)
        {
            binding.tvTitle.text = toDo.title
            binding.tvDueDate.text = toDo.dueDate
            binding.tvDescription.text = toDo.description
            when (toDo.priority)
            {
                Prioirities.LOW.name -> binding.ivPriority.setBackgroundResource(R.drawable.prio_green)
                Prioirities.MEDIUM.name -> binding.ivPriority.setBackgroundResource(R.drawable.prio_orange)
                Prioirities.HIGH.name -> binding.ivPriority.setBackgroundResource(R.drawable.prio_red)
            }
        }

        override fun onClick(view: View)
        {
            val navController: NavController = Navigation.findNavController(view)
            val action = RecyclerViewFragmentDirections.actionRecyclerFragmentToAddToDoDialog(listOfToDos[adapterPosition], adapterPosition)
            navController.navigate(action)
        }
    }
}