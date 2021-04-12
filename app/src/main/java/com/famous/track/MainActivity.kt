package com.famous.track

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment.newInstance(), true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.naavigation_bar, menu)
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
       when (item.itemId) {
           R.id.nav_settings -> Toast.makeText(this, "Show Settings", Toast.LENGTH_SHORT).show()
           R.id.nav_developer -> Toast.makeText(this, "Show Developer", Toast.LENGTH_SHORT).show()
           R.id.nav_share -> Toast.makeText(this, "Show Share", Toast.LENGTH_SHORT).show()
           R.id.about -> Toast.makeText(this, "Show About", Toast.LENGTH_SHORT).show()

       }
        return super.onOptionsItemSelected(item)
    }
     fun replaceFragment(fragment: Fragment, istransition: Boolean)
    {
        val fragmentTransition = supportFragmentManager.beginTransaction()

        if (istransition)
        {
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragments = supportFragmentManager.fragments
        if (fragments.size == 0){
            finish()
        }
    }
}