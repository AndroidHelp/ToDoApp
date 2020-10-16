package com.katariya.todoapp.ui.todo.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.katariya.todoapp.R
import com.katariya.todoapp.ui.todo.ToDoRepository
import kotlinx.android.synthetic.main.fragment_add_to_do.*

class AddToDoFragment : Fragment(), AddToDoView {
    private lateinit var viewModel: AddToDoViewModel
    private val args: AddToDoFragmentArgs by navArgs()
    private var itemId = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        args.todoItem?.let {
            itemId = it.id
            tietTitle.setText(it.title)
            tietDescription.setText(it.description)
        }

        val toDoViewModelFactory =
            AddToDoViewModelFactory(ToDoRepository(requireActivity().application))
        viewModel = ViewModelProvider(
            requireActivity(),
            toDoViewModelFactory
        ).get(AddToDoViewModel::class.java)
        viewModel.addToDoView = this

        tietTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) tilTitle.error = null
            }
        })

        tietDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) tilDescription.error = null
            }
        })

        btnSave.setOnClickListener {
            val title = tietTitle.text.toString().trim()
            val description = tietDescription.text.toString().trim()
            viewModel.validateUpdateInsert(title, description, itemId)
            requireView().findNavController().navigateUp()
        }
    }

    override fun validateTitle(message: Int) {
        tilTitle.error = getString(message)
    }

    override fun validateDescription(message: Int) {
        tilDescription.error = getString(message)
    }
}