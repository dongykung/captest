package com.example.capstone_android

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import com.example.capstone_android.databinding.ActivityCreateBinding

class CreateActivity: AppCompatActivity() {
    private lateinit var binding:ActivityCreateBinding
    var createViewFragment=CreateViewFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar=binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.textview.text="클럽개설"

        supportFragmentManager.beginTransaction().replace(R.id.createfragment,createViewFragment).commit()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        val newintent= Intent()
        newintent.putExtra("result",1)
        setResult(RESULT_OK,newintent)
    }


}
