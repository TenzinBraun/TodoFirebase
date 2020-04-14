package fr.iutbourg.todofirebase.ui.adapter

import android.view.View.GONE
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
        var itemTodo = todoList[position]
        holder.bindData(todoList[position])
        holder.itemView.setOnLongClickListener {
            it.containerAction.visibility = VISIBLE
            true
        }

        holder.itemView.editTodo.setOnClickListener {
            holder.itemView.containerAction.visibility = GONE
            holder.itemView.editTodoText.visibility = VISIBLE
            holder.itemView.containerEdit.visibility = VISIBLE
            holder.itemView.nameTask.visibility = GONE
        }

        holder.itemView.deleteTodo.setOnClickListener {
            callback.deleteTodo(todoList[position])
            holder.itemView.containerAction.visibility = GONE
        }

        holder.itemView.validateEdit.setOnClickListener{
            itemTodo.name = holder.itemView.editTodoText.text.toString()
            callback.editTodo(position, itemTodo)
            holder.itemView.editTodoText.visibility = GONE
            holder.itemView.containerEdit.visibility = GONE
            holder.itemView.nameTask.visibility = VISIBLE
        }

        holder.itemView.cancel.setOnClickListener{
            holder.itemView.editTodoText.visibility = GONE
            holder.itemView.containerEdit.visibility = GONE
            holder.itemView.nameTask.visibility = VISIBLE
        }

    }

    fun submitList(listTodo: List<Todo>) {
        this.todoList = listTodo
        notifyDataSetChanged()
    }

}
