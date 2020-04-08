package fr.iutbourg.todofirebase.ui.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.iutbourg.todofirebase.R

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    companion object {
        fun create(parent: ViewGroup): TodoViewHolder {
            val view = LayoutInflater.from(
                parent.context
            ).inflate(R.layout.todo_view_holder, parent, false)
            return TodoViewHolder(view)
        }
    }

}
