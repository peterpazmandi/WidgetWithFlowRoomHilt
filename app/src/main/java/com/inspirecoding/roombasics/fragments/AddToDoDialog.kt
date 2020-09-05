package com.inspirecoding.roombasics.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.inspirecoding.roombasics.R
import com.inspirecoding.roombasics.databinding.FragmentAddToDoDialogBinding
import com.inspirecoding.roombasics.enums.Prioirities
import com.inspirecoding.roombasics.model.ToDo
import com.inspirecoding.roombasics.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add_to_do_dialog.*

class AddToDoDialog : DialogFragment()
{
    private lateinit var binding: FragmentAddToDoDialogBinding
    private val toDoViewModel by
        navGraphViewModels<ToDoViewModel>(R.id.navigation_graph)

    override fun onCreateView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_to_do_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs: AddToDoDialogArgs by navArgs()
        val toDo = safeArgs.toDo

        initSpinner()

        toDo?.let {
            binding.tvDialogTitle.text = getString(R.string.modify_todo)
            binding.btnAdd.text = getString(R.string.modify)
            populateForm(it)
        }

        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.btnAdd.setOnClickListener {
            if(toDo != null)
            {
                val updatedToDo = createToDo()
                updatedToDo.toDoId = toDo.toDoId
                toDoViewModel.updateToDo(updatedToDo)
            }
            else
            {
                toDoViewModel.addToDo(createToDo())
            }
            dismiss()
        }
    }

    private fun populateForm(toDo: ToDo)
    {
        binding.etTitle.setText(toDo.title)
        binding.etDescription.setText(toDo.description)

        when(toDo.priority)
        {
            Prioirities.LOW.name -> binding.srPriority.setSelection(0)
            Prioirities.MEDIUM.name -> binding.srPriority.setSelection(1)
            Prioirities.HIGH.name -> binding.srPriority.setSelection(2)
        }
        val day = toDo.dueDate.substringBefore('.').toInt()
        val month = toDo.dueDate.substringBeforeLast('.').substringAfter('.').toInt()
        val year = toDo.dueDate.substringAfterLast('.').toInt()
        binding.dpDueDate.updateDate(year, month, day)
    }

    private fun initSpinner()
    {
        activity?.let {
            val arraySpinner = arrayOf (
                it.getString(R.string.low),
                it.getString(R.string.medium),
                it.getString(R.string.high)
            )

            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(it.applicationContext, android.R.layout.simple_spinner_item, arraySpinner)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sr_priority.adapter = adapter
        }
    }
    private fun createToDo() = ToDo(
        0,
        binding.etTitle.text.toString(),
        "${String.format("%02d", binding.dpDueDate.dayOfMonth)}.${String.format("%02d", binding.dpDueDate.month)}.${String.format("%04d", binding.dpDueDate.year)}",
        binding.etDescription.text.toString(),
        when(binding.srPriority.selectedItemId)
        {
            0L -> Prioirities.LOW.name
            1L -> Prioirities.MEDIUM.name
            else -> Prioirities.HIGH.name
        }
    )
}
