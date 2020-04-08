package fr.iutbourg.todofirebase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.iutbourg.todofirebase.R
import fr.iutbourg.todofirebase.data.model.Todo
import fr.iutbourg.todofirebase.ui.adapter.TodoAdapter
import fr.iutbourg.todofirebase.ui.dialog.TodoAddElementDialog
import fr.iutbourg.todofirebase.ui.viewmodel.MainViewModel
import fr.iutbourg.todofirebase.ui.widget.ActionCallback
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(), ActionCallback {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TodoAdapter
    private var listTodo =  mutableListOf<Todo>()
    private lateinit var database: DatabaseReference
    private val db = FirebaseDatabase.getInstance().reference



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProvider(this,
                MainViewModel
            ).get()
        } ?: throw IllegalStateException("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TodoAdapter(this)

        db.setValue("Hello World")
        addNewElement.setOnClickListener {
            val dialog = TodoAddElementDialog(this, requireActivity())
            dialog.show()
        }

    }

    override fun editTodo(todo: Todo) {
        TODO("Not yet implemented")
    }

    override fun deleteTodo(todo: Todo) {
        listTodo.remove(todo)
    }

}
