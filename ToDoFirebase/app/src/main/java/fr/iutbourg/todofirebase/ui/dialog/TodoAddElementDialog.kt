package fr.iutbourg.todofirebase.ui.dialog

import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import fr.iutbourg.todofirebase.R
import fr.iutbourg.todofirebase.ui.widget.ActionCallback

class TodoAddElementDialog(callback: ActionCallback, context: FragmentActivity) : BaseDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(R.layout.add_element_modal)
        setSizeForDialog()
    }

}
