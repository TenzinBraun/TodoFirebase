package fr.iutbourg.todofirebase.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import fr.iutbourg.todofirebase.MainActivity
import fr.iutbourg.todofirebase.R
import fr.iutbourg.todofirebase.data.model.Todo
import fr.iutbourg.todofirebase.ui.adapter.TodoAdapter
import fr.iutbourg.todofirebase.ui.dialog.TodoAddElementDialog
import fr.iutbourg.todofirebase.ui.viewmodel.MainViewModel
import fr.iutbourg.todofirebase.ui.widget.ActionCallback
import fr.iutbourg.todofirebase.ui.widget.MyCallback
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import java.util.regex.Pattern

class MainFragment : Fragment(), ActionCallback, MyCallback {

    private var ascending: Boolean = false
    private lateinit var viewModel: MainViewModel
    private lateinit var todoAdapter: TodoAdapter
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
        setHasOptionsMenu(true)

        viewModel.callback = this
        todoAdapter = TodoAdapter(this)
        view.reclycerViewTodo.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.startListeningToAnyChange()

        view.addNewElement.setOnClickListener {
            val dialog = TodoAddElementDialog(this, requireActivity())
            dialog.show()
        }

        (activity as? MainActivity)?.supportActionBar?.apply {
            this.setTitle(R.string.app_toolbar_title)
            this.setDisplayHomeAsUpEnabled(false)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_menu, menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.searchItem)
        val filterMenuItem = menu.findItem(R.id.sortItem)
        val searchMenuView = searchMenuItem.actionView as SearchView



        filterMenuItem.setOnMenuItemClickListener {
            ascending = if (!ascending) {
                todoAdapter.submitList(listTodo.sortedBy {
                    it.name
                })
                true
            } else {
                todoAdapter.submitList(listTodo.sortedByDescending {
                    it.name
                })
                false
            }
            true
        }

        searchMenuView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchMenuView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    findTodoWith(query).run {
                        todoAdapter.submitList(this)
                    }
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    return true
                }
                return false
            }
        })
    }

    private fun findTodoWith(query: String) : List<Todo> {
        if(query.isEmpty()){
            return listTodo
        }
        val tempFilter = listTodo
        return tempFilter.filter {
            Pattern.compile(
                    Pattern.quote(query),
                    Pattern.CASE_INSENSITIVE
                )
                .matcher(it.name)
                .find()
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
        todoAdapter.submitList(todoList)
    }
}
