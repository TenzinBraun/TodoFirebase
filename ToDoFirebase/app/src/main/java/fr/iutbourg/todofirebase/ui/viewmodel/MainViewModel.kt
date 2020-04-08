package fr.iutbourg.todofirebase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import fr.iutbourg.todofirebase.data.model.Todo


class MainViewModel : ViewModel() {
    private var todoList = mutableListOf<Todo>()

    fun loadInformationFromFirebase(): MutableList<Todo> {
        val ref = FirebaseDatabase.getInstance().reference.child("tasks")
        ref.addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(snapchot: DataSnapshot) {
                    Log.d("Firebase", "firebase")
                }

            }
        )

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
