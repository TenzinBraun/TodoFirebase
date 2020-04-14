package fr.iutbourg.todofirebase.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import fr.iutbourg.todofirebase.R
import fr.iutbourg.todofirebase.data.model.Todo
import fr.iutbourg.todofirebase.ui.adapter.TodoAdapter
import fr.iutbourg.todofirebase.ui.dialog.TodoAddElementDialog
import fr.iutbourg.todofirebase.ui.viewmodel.MainViewModel
import fr.iutbourg.todofirebase.ui.widget.ActionCallback
import fr.iutbourg.todofirebase.ui.widget.MyCallback
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment(), ActionCallback, MyCallback {

    private var ascending: Boolean = false
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TodoAdapter
    private var listTodo = mutableListOf<Todo>()
    private val db = FirebaseDatabase.getInstance().reference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.run {
            viewModel = ViewModelProvider(
                this,
                MainViewModel
            ).get()
        } ?: throw IllegalStateException("Invalid Activity")
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.callback = this
        adapter = TodoAdapter(this)
        view.reclycerViewTodo.adapter = adapter
        view.reclycerViewTodo.layoutManager = LinearLayoutManager(requireContext())
        viewModel.startListeningToAnyChange()
        addNewElement.setOnClickListener {
            val dialog = TodoAddElementDialog(this, requireActivity())
            dialog.show()
        }
        sortTodoList.setOnClickListener {
            ascending = if (!ascending) {
                adapter.submitList(listTodo.sortedBy {
                    it.name
                })
                true
            } else {
                adapter.submitList(listTodo.sortedByDescending {
                    it.name
                })
                false
            }
        }
    }

    override fun editTodo(position: Int, todo: Todo) {
        db.child("tasks").child(todo.key!!).setValue(Todo(null, todo.name, todo.check))
    }

    override fun deleteTodo(todo: Todo) {
        db.child("tasks").child(todo.key!!).removeValue()
    }

    override fun addTodo(name: String) {
        db.child("tasks").push().setValue(Todo(null, name, false))
    }

    override fun notifyAllEntries(todoList: MutableList<Todo>) {
        listTodo = todoList
        adapter.submitList(todoList)
    }
}
