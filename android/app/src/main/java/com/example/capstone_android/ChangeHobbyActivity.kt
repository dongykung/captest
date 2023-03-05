package com.example.capstone_android


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import com.example.capstone_android.databinding.ActivityChangehobbyBinding


class ChangeHobbyActivity:AppCompatActivity() {
    private lateinit var binding: ActivityChangehobbyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangehobbyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar=binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.textview.text="관심사 재설정"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.changehobby_toolbar,menu)
        val confirm=menu?.findItem(R.id.toolbar_next_button)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.toolbar_next_button->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}