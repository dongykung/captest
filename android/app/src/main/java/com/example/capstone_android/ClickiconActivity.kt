package com.example.capstone_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstone_android.data.ClubData
import com.example.capstone_android.data.getclubuid
import com.example.capstone_android.databinding.ActivityClickiconBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.item_main.view.*

class ClickiconActivity: AppCompatActivity() {
    private lateinit var binding: ActivityClickiconBinding
    lateinit var hobby:String
    lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hobby= intent.getStringExtra("hobby").toString()
        binding = ActivityClickiconBinding.inflate(layoutInflater)
        db= Firebase.firestore

        setContentView(binding.root)
        val toolbar=binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.textview.text=hobby


        binding.detailviewfragmentRecyclerview.adapter=DetailViewRecyclerViewAdapter()
        binding.detailviewfragmentRecyclerview.layoutManager= LinearLayoutManager(this)

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

    @SuppressLint("NotifyDataSetChanged")
    inner class DetailViewRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var clubdata:ArrayList<ClubData> = arrayListOf()

        init{
            clubdata.clear()
            db.collection("category").document(hobby).get().addOnSuccessListener{   snapshot->
                val item=snapshot.toObject(getclubuid::class.java)
                if (item?.RoomId != null){
                    for(data in item.RoomId!!){
                        db.collection("meeting_room").document(data).get().addOnSuccessListener{    snapshot2->
                            val item3 = snapshot2.toObject(ClubData::class.java)
                            clubdata.add(item3!!)
                            clubdata.sortByDescending { it.upload_time }
                            binding.detailviewfragmentRecyclerview.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            }

        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view= LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent,false)
            return CustomViewHolder(view)

        }
        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }
        @SuppressLint("CheckResult", "SuspiciousIndentation")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewholder=(holder as CustomViewHolder).itemView
            Glide.with(holder.itemView.context).load(clubdata[position].imageUrl).apply(
                RequestOptions().circleCrop()).into(viewholder.detailviewitem_imageview_content)
            viewholder.ClubName.text=clubdata[position].title
            viewholder.NumberCount.text= clubdata[position].max.toString()
            viewholder.ClubExplain.text=clubdata[position].info_text
            viewholder.CardView.setOnClickListener{
                var intent= Intent(applicationContext,MeetingRoomActivity::class.java)
                intent.putExtra("meeting_room_id",clubdata[position].Uid)
                startActivity(intent)
                println(clubdata[position].Uid)
            }
        }


        override fun getItemCount(): Int {
            return clubdata.size
        }

    }



}