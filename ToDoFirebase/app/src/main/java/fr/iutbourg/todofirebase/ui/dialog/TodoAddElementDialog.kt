package fr.iutbourg.todofirebase.ui.dialog

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import fr.iutbourg.todofirebase.R
import fr.iutbourg.todofirebase.ui.widget.ActionCallback
import kotlinx.android.synthetic.main.add_element_modal.*
import kotlinx.android.synthetic.main.todo_view_holder.*

class TodoAddElementDialog(private val callback: ActionCallback, context: FragmentActivity) : BaseDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(R.layout.add_element_modal)
        setSizeForDialog()
        validateTodo.setOnClickListener {
            callback.addTodo(newTodo?.text.toString())
            this.dismiss()
        }
    }

}
