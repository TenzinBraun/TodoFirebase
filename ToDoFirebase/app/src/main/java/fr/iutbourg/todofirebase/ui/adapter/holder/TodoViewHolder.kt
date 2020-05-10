package fr.iutbourg.todofirebase.ui.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.iutbourg.todofirebase.R
import fr.iutbourg.todofirebase.data.model.Todo
import kotlinx.android.synthetic.main.todo_view_holder.view.*
import java.util.*

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(todo: Todo) {
        itemView.nameTask.text = todo.name
        itemView.dateTimeCreated.text = Date(todo.createdAt!!).toString()
    }

    companion object {
        fun create(parent: ViewGroup): TodoViewHolder {
            val view = LayoutInflater.from(
                parent.context
            ).inflate(R.layout.todo_view_holder, parent, false)
            return TodoViewHolder(view)
        }
    }

}
