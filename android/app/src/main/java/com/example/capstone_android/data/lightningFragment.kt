package com.example.capstone_android.data

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstone_android.MeetingRoomActivity
import com.example.capstone_android.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_lightning.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_main.view.*

class lightningFragment: Fragment() {
    lateinit var db : FirebaseFirestore
    lateinit var lightlist : ArrayList<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= LayoutInflater.from(activity).inflate(R.layout.fragment_lightning,container,false)
        db= Firebase.firestore

        val lightning = arrayListOf<String>("운동","친목/인맥","게임/오락","댄스/무용","공연/축제","음악","공부/토론","자유주제")
        val lightsport = arrayListOf<String>("축구","농구","테니스","탁구","배드민턴","배구","족구","수영","골프")
        val radiogroup=view.lightgrid
        radiogroup.columnCount=4


        view.sport.setOnClickListener{

            for((index,data) in lightsport.withIndex()){
                val btn= Button(context)
                btn.text=data
                btn.setOnClickListener{
                    getlightdata("운동",data)
                }
                radiogroup.addView(btn)
            }

        }
        view.lightrecycler.adapter=LightViewRecyclerViewAdapter()
        view.lightrecycler.layoutManager= LinearLayoutManager(activity)
        return view
    }
    fun getlightdata(hobby:String,sport:String){
        db.collection("light").document(hobby).collection(sport).get().addOnSuccessListener{    document->
            for(data in document){
                println(data)
            }
        }
        db.collection("light").document(hobby).collection(sport).addSnapshotListener{querySnapshot, firebaseFirestoreException ->
            if (querySnapshot == null) return@addSnapshotListener
            for (snapshot in querySnapshot.documents) {
              val item=snapshot.toObject(lightData::class.java)
                println(item?.title)

            }
        }
    }
    inner class LightViewRecyclerViewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        //var clubdata:ArrayList<ClubData> = arrayListOf()

        init{


        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view=LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent,false)
            return CustomViewHolder(view)

        }
        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }
        @SuppressLint("CheckResult", "SuspiciousIndentation")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        }


        override fun getItemCount(): Int {
            return 4
        }

    }
}