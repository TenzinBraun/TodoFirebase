package fr.iutbourg.todofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import fr.iutbourg.todofirebase.ui.fragment.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(appToolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        appToolbar.setNavigationOnClickListener {
            onNavigateUp()
        }
    }

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.appFragmentContainer).navigateUp()
    }

}
