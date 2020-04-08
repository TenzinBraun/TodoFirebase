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
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment(), ActionCallback {

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
        adapter = TodoAdapter(this)
        view.reclycerViewTodo.adapter = adapter
        view.reclycerViewTodo.layoutManager = LinearLayoutManager(requireContext())
        listTodo = viewModel.loadInformationFromFirebase()
        addNewElement.setOnClickListener {
            val dialog = TodoAddElementDialog(this, requireActivity())
            dialog.show()
        }
    }

    override fun editTodo(position: Int, todo: Todo) {
        listTodo[position].name = todo.name
        adapter.submitList(listTodo)
    }

    override fun deleteTodo(todo: Todo) {
        listTodo.remove(todo)
        adapter.submitList(listTodo)
        val ref =  db.child("tasks")
        ref.setValue(listTodo)
        ref.push()
    }

    override fun addTodo(name: String) {
        listTodo.add(Todo(name, false))
        adapter.submitList(listTodo)
        val ref =  db.child("tasks")
        ref.setValue(listTodo)
        ref.push()
    }

}
