package fr.iutbourg.todofirebase.ui.widget

import fr.iutbourg.todofirebase.data.model.Todo

interface ActionCallback {
    fun editTodo(todo: Todo)
    fun deleteTodo(todo: Todo)
}
