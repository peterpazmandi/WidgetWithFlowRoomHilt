package com.inspirecoding.roombasics.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inspirecoding.roombasics.R
import com.inspirecoding.roombasics.adapter.ToDoAdapter
import com.inspirecoding.roombasics.databinding.FragmentRecyclerViewBinding
import com.inspirecoding.roombasics.viewmodel.ToDoViewModel

class RecyclerViewFragment : Fragment()
{
    private lateinit var binding: FragmentRecyclerViewBinding

    private lateinit var toDoAdapter: ToDoAdapter
    private val toDoViewModel by navGraphViewModels<ToDoViewModel>(R.id.navigation_graph)

    private val itemTouchHelper by lazy {

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean
            {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
            {
                val from = viewHolder.adapterPosition
                toDoViewModel.deleteToDo(toDoAdapter.getItemFromPosition(position = from))
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int)
            {
                super.onSelectedChanged(viewHolder, actionState)
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder)
            {
                super.clearView(recyclerView, viewHolder)
            }
        }

        ItemTouchHelper(simpleItemTouchCallback)
    }

    override fun onStart()
    {
        super.onStart()

        (activity as AppCompatActivity).supportActionBar?.show()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View?
    {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_recycler_view,
            container,
            false)

        context?.let {  _context ->
            toDoAdapter = ToDoAdapter(_context)
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = toDoAdapter
            }
        }

        toDoViewModel.listOfToDosLiveData.observe(viewLifecycleOwner) { _list ->
            toDoAdapter.addToDos(_list)
            toDoAdapter.notifyDataSetChanged()
        }

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_recyclerFragment_to_addToDoDialog)
        }
    }
}
