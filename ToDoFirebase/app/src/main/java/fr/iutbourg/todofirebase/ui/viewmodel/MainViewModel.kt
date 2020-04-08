package fr.iutbourg.todofirebase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.iutbourg.todofirebase.data.model.Todo


class MainViewModel : ViewModel() {
    private var todoList = mutableListOf<Todo>()

    fun loadInformationFromFirebase(): MutableList<Todo> {
        val ref = FirebaseDatabase.getInstance().reference.child("tasks")
        ref.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.value.toString()
                    todoList = collectPhoneNumbers(dataSnapshot.value as Map<*, *>?)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("FireBaseError", "Got Error while loading data")
                }
            })
        return todoList
    }

    private fun collectPhoneNumbers(map: Map<*, *>?): MutableList<Todo> {
        val list = mutableListOf<Todo>()
        map?.let {
            for ((_, value) in it.entries) {
                list.add(value as Todo)
            }
        }
        return list
    }


    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }
}
