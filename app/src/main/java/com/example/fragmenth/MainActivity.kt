package com.example.fragmenth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val listFragment = ContactListFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, listFragment)
                .commit()
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment is ContactListFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}