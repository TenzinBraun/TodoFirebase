package fr.iutbourg.todofirebase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import fr.iutbourg.todofirebase.data.model.Todo
import fr.iutbourg.todofirebase.ui.widget.MyCallback


class MainViewModel : ViewModel() {
    private var todoList = mutableListOf<Todo>()
    lateinit var callback: MyCallback

    private fun getTodo(map: Map<String, HashMap<String, Any>>?): MutableList<Todo> {
        val list = mutableListOf<Todo>()
        map?.let {
            for ((key, value) in it.entries) {
                list.add(Todo(key, value))
            }
        }
        return list
    }

    fun startListeningToAnyChange() {
        val ref = FirebaseDatabase.getInstance().reference.child("tasks")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                todoList = getTodo(snapshot.value as Map<String, HashMap<String, Any>>?)
                callback.notifyAllEntries(todoList)
            }
        })
    }


    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }
}
