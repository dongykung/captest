package com.example.capstone_android


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import com.example.capstone_android.data.SignUpData
import com.example.capstone_android.databinding.ActivityChangehobbyBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.view.*


class ChangeHobbyActivity:AppCompatActivity() {
    private lateinit var binding: ActivityChangehobbyBinding
    lateinit var db : FirebaseFirestore
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangehobbyBinding.inflate(layoutInflater)
        db=Firebase.firestore
        val myhobbylist:ArrayList<String> = ArrayList<String>()
        val allhobbylist:ArrayList<String> = ArrayList<String>()
        allhobbylist.add("공예")
        allhobbylist.add("독서")
        allhobbylist.add("자동차")
        allhobbylist.add("요리")
        allhobbylist.add("댄스")
        allhobbylist.add("게임")
        allhobbylist.add("사교")
        allhobbylist.add("음악")
        allhobbylist.add("애완동물")
        allhobbylist.add("사진")
        allhobbylist.add("운동")
        allhobbylist.add("스터디그룹")
        allhobbylist.add("여행")
        allhobbylist.add("봉사활동")

        setContentView(binding.root)
        val imagegrid=binding.imagegrid
        imagegrid.columnCount=5
        val toolbar=binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.textview.text="관심사 재설정"

        db.collection("user").document(Firebase.auth.currentUser?.uid.toString()).get().addOnSuccessListener{document->
            val item=document.toObject(SignUpData::class.java)
            for(data in item?.interest_array!!){
                myhobbylist.add(data)
            }
            for(data in allhobbylist){
                if(myhobbylist.contains(data)){ val param=GridLayout.LayoutParams()
                    val checkbox=CheckBox(applicationContext)
                    checkbox.isChecked=true
                    checkbox.setButtonDrawable(R.color.transparent)
                    checkbox.setBackgroundResource(R.color.transparent)
                    checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_cook,0,0)
                    checkhobby(data,checkbox,imagegrid,checkbox.isChecked)
                }
                else{
                    val checkbox=CheckBox(applicationContext)
                    checkbox.isChecked=false
                    checkbox.setButtonDrawable(R.color.transparent)
                    checkbox.setBackgroundResource(R.color.transparent)
                    checkhobby(data,checkbox,imagegrid,checkbox.isChecked)
                }
            }

        }



    }
    @SuppressLint("SetTextI18n")
    fun checkhobby(hobby:String, checkbox: CheckBox, imagegrid:GridLayout,check:Boolean){
        if(hobby=="운동") {
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="음악"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="여행"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="사교"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="독서"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="요리"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="사진"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="게임"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="댄스"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="자동차"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="애완동물"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="공예"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="봉사활동"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
        if(hobby=="스터디그룹"){
            val param = GridLayout.LayoutParams()
            checkbox.text = "     $hobby"
            checkbox.isChecked = true
            param.width = 165
            param.height = 230
            param.marginStart = 30
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
        }
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