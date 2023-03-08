package com.example.capstone_android


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_android.data.SignUpData
import com.example.capstone_android.databinding.ActivityChangehobbyBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.view.*


class ChangeHobbyActivity:AppCompatActivity() {
    private lateinit var binding: ActivityChangehobbyBinding
    lateinit var db : FirebaseFirestore
    lateinit var myhobbylist:ArrayList<String>
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangehobbyBinding.inflate(layoutInflater)
        db=Firebase.firestore
        myhobbylist = ArrayList<String>()
        val allhobbylist = arrayListOf<String>(
                "운동", "여행", "음악", "사교", "독서",
                "요리", "사진", "게임", "댄스", "자동차",
                "애완동물", "공예", "봉사활동", "스터디그룹")

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
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_sport,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "    $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="음악"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_music,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "      $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="여행"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_trip,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "      $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="사교"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_job,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "      $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="독서"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_read,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "      $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="요리"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_cook,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "      $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="사진"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_photo,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "      $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="게임"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_game,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "      $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="댄스"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_dance,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "      $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="자동차"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_car,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "      $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="애완동물"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_pet,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "  $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="공예"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_art,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "     $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="봉사활동"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_volunteer,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= "    $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
        if(hobby=="스터디그룹"){
            val param = GridLayout.LayoutParams()
            param.width = 190
            param.height = 230
            param.marginStart = 15
            checkbox.layoutParams = param
            imagegrid.addView(checkbox)
            checkbox.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable._checkbox_study,0,0)
            checkbox.setBackgroundResource(R.color.transparent)
            checkbox.text= " $hobby"
            checkbox.setOnCheckedChangeListener{ _,ischecked ->
                if(ischecked) myhobbylist.add(hobby)
                else myhobbylist.remove(hobby)
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.changehobby_toolbar,menu)
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
                println(myhobbylist)
                val data = hashMapOf("interest_array" to myhobbylist)
                db.collection("user").document(Firebase.auth.currentUser?.uid.toString()).set(data, SetOptions.merge())
                finish()
                return true
            }
            R.id.cancel->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}