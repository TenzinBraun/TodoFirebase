package fr.iutbourg.todofirebase.ui.adapter

import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.iutbourg.todofirebase.data.model.Todo
import fr.iutbourg.todofirebase.ui.adapter.holder.TodoViewHolder
import fr.iutbourg.todofirebase.ui.widget.ActionCallback
import kotlinx.android.synthetic.main.todo_view_holder.view.*

class TodoAdapter(private val callback: ActionCallback) : RecyclerView.Adapter<TodoViewHolder>() {

    private var todoList = emptyList<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
        TodoViewHolder.create(parent)

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.setOnLongClickListener {
            it.containerAction.visibility = VISIBLE
            true
        }

        holder.itemView.editTodo.setOnClickListener {
            callback.editTodo(position, todoList[position])
        }

        holder.itemView.deleteTodo.setOnClickListener {
            callback.deleteTodo(todoList[position])
        }

    }

    fun submitList(listTodo: List<Todo>) {
        this.todoList = listTodo
        notifyDataSetChanged()
    }

}
