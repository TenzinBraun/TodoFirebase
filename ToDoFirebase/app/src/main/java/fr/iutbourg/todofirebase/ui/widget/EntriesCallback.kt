package fr.iutbourg.todofirebase.ui.widget

import fr.iutbourg.todofirebase.data.model.Todo

interface EntriesCallback {
    fun notifyAllEntries(todoList: MutableList<Todo>)
}
