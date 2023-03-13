package com.example.capstone_android
import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.capstone_android.data.lightningFragment
import com.example.capstone_android.databinding.ActivityHomeBinding
import com.example.capstone_android.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class HomeActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar=binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.textview.text="성북구"

        val detailViewFragment=DetailViewFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_content,detailViewFragment).commit()
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        binding.bottomNavigation.setOnItemSelectedListener{
            when(it.itemId){
                R.id.action_home ->{
                    val detailViewFragment=DetailViewFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,detailViewFragment).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.action_gps ->{
                    val mapViewFragment=MapFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,mapViewFragment).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.action_Lightning->{
                    val lightningFragment=lightningFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_content,lightningFragment).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.action_account ->{
                    val intent = Intent(this,ProfileActivity::class.java)
                    startActivity(intent)

                    return@setOnItemSelectedListener true
                }
                R.id.action_more ->{

                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}