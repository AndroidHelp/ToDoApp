package com.katariya.todoapp.ui.todo

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.katariya.todoapp.R
import com.katariya.todoapp.data.database.ToDoItem
import com.katariya.todoapp.data.pref.AppPreferences
import com.katariya.todoapp.ui.todo.pin.PinHeaderSection
import com.katariya.todoapp.ui.todo.pin.PinToDoAdapter
import com.katariya.todoapp.ui.todo.pin.PinToDoItemClickListener
import kotlinx.android.synthetic.main.fragment_todo_list.*

class ToDoListFragment : Fragment(), ToDoItemClickListener, PinToDoItemClickListener {

    private lateinit var viewModel: ToDoViewModel
    private lateinit var pinHeaderSection: PinHeaderSection
    private lateinit var pinToDoAdapter: PinToDoAdapter
    private lateinit var toDoHeaderSection: ToDoHeaderSection
    private lateinit var toDoAdapter: ToDoAdapter
    private var toDoList = ArrayList<ToDoItem>()
    private var pinnedToDoList = ArrayList<ToDoItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_todo, menu)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.app_bar_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            val queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    toDoAdapter.filter.filter(newText.toString())
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
            }
            searchView.setOnQueryTextListener(queryTextListener)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_addToDo -> {
                requireView().findNavController()
                    .navigate(R.id.action_toDoListFragment_to_addToDoFragment)
                true
            }
            else -> NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!AppPreferences.getLoginStatus()) {
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment)
            return
        }
        setHasOptionsMenu(true)

        //adapters
        pinHeaderSection = PinHeaderSection()
        pinToDoAdapter = PinToDoAdapter(requireContext(), pinnedToDoList, this)

        toDoHeaderSection = ToDoHeaderSection()
        toDoAdapter = ToDoAdapter(requireContext(), toDoList, this)

        val concatAdapter =
            ConcatAdapter(pinHeaderSection, pinToDoAdapter, toDoHeaderSection, toDoAdapter)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = false
        rcvToDo.layoutManager = layoutManager
        rcvToDo.adapter = concatAdapter

        val toDoViewModelFactory =
            ToDoViewModelFactory(ToDoRepository(requireActivity().application))
        viewModel = ViewModelProvider(
            requireActivity(),
            toDoViewModelFactory
        ).get(ToDoViewModel::class.java)
        viewModel.getToDoItems()?.observe(viewLifecycleOwner, {
            this.renderToDoItems(
                it
            )
        })
        viewModel.getPinToDoItems()?.observe(viewLifecycleOwner, {
            this.pinToDoItems(
                it
            )
        })
    }

    private fun renderToDoItems(it: List<ToDoItem>?) {
        if (toDoList.isNotEmpty()) toDoList.clear()
        toDoList.addAll(it!!)
        if (it.isNotEmpty()) toDoHeaderSection.sectionSize = 1
        else toDoHeaderSection.sectionSize = 0
        toDoHeaderSection.notifyDataSetChanged()
        toDoAdapter.notifyDataSetChanged()
    }

    private fun pinToDoItems(it: List<ToDoItem>?) {
        if (pinnedToDoList.isNotEmpty()) pinnedToDoList.clear()
        pinnedToDoList.addAll(it!!)
        if (it.isNotEmpty()) pinHeaderSection.sectionSize = 1
        else pinHeaderSection.sectionSize = 0
        pinHeaderSection.notifyDataSetChanged()
        pinToDoAdapter.notifyDataSetChanged()
    }

    override fun onItemDelete(toDoItem: ToDoItem) {
        viewModel.deleteToDo(toDoItem)
    }

    override fun onItemPin(toDoItem: ToDoItem) {
        viewModel.updateToDo(toDoItem)
    }

    override fun onPinItemDelete(toDoItem: ToDoItem) {
        viewModel.updateToDo(toDoItem)
    }

}