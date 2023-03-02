package com.example.capstone_android

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstone_android.data.SignUpData
import com.example.capstone_android.data.getclubuid
import com.example.capstone_android.data.ClubData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_main.*

import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_main.view.*



class DetailViewFragment: Fragment() {

    lateinit var db : FirebaseFirestore
    var scrapMainLayout:GridLayout?=null
    var clubdata:ArrayList<ClubData> = arrayListOf()
    var clubroomuid:ArrayList<String> =arrayListOf()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= LayoutInflater.from(activity).inflate(R.layout.fragment_main,container,false)
        db= Firebase.firestore
        val uid= Firebase.auth.currentUser?.uid
        val fab:FloatingActionButton=view.CreateClub
        val recyclerView:RecyclerView=view.detailviewfragment_recyclerview
        val nestedscrollview:NestedScrollView=view.nestedScrollView
        nestedscrollview.setOnScrollChangeListener(object: NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if(scrollY>oldScrollY){
                    fab.hide()
                }
                if(scrollY<oldScrollY){
                    fab.show()
                }
            }

        })

        fab.setOnClickListener{
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(activity, CreateActivity::class.java)
                intent.putExtra("create","hello")
                startActivityForResult(intent,9)
            } else {
                Toast.makeText(activity, "스토리지 읽기 권한이 없습니다.", Toast.LENGTH_LONG).show()
            }
        }

        scrapMainLayout=view.imagegrid
        scrapMainLayout?.columnCount=5



        view.detailviewfragment_recyclerview.adapter=DetailViewRecyclerViewAdapter()
        view.detailviewfragment_recyclerview.layoutManager=LinearLayoutManager(activity)
        /*
        view.refresh_layout.setOnRefreshListener {
            //clubdata.shuffle()
            update()
            view.detailviewfragment_recyclerview.adapter?.notifyDataSetChanged()
            refresh_layout.isRefreshing = false
        }

         */


        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        update()
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    fun update() {

        clubdata.clear()
        db.collection("user").document(Firebase.auth.currentUser?.uid.toString()).get().addOnSuccessListener{   document->
            val item=document.toObject(SignUpData::class.java)
            view?.UserName?.text=item?.nickname+"님을"
            for(data in item?.interest_array!!){
                db.collection("category").document(data).get().addOnSuccessListener { document2 ->
                    val item2 = document2.toObject(getclubuid::class.java)
                    if (item2?.RoomId != null){
                        for (data2 in item2.RoomId!!) {
                            db.collection("meeting_room").document(data2).get()
                                .addOnSuccessListener { document3 ->
                                    val item3 = document3.toObject(ClubData::class.java)
                                    if(item3!=null){
                                        clubdata.add(item3!!)
                                        clubdata.sortByDescending { it.upload_time }
                                        view?.detailviewfragment_recyclerview?.adapter?.notifyDataSetChanged()
                                    }

                                }
                        }
                    }
                }.addOnFailureListener{}
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged", "ResourceAsColor", "SetTextI18n")
    inner class DetailViewRecyclerViewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        //var clubdata:ArrayList<ClubData> = arrayListOf()

        init{
            clubdata= arrayListOf()
            clubdata.clear()
            db.collection("user").document(Firebase.auth.currentUser?.uid.toString()).get().addOnSuccessListener{   document->
                val item=document.toObject(SignUpData::class.java)
                view?.UserName?.text=item?.nickname+"님을"
                for(data in item?.interest_array!!){
                    if(data=="운동"){
                        val param=GridLayout.LayoutParams()
                        val test=Button(context)
                        //test2.setBackgroundColor(R.color.not)
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_sports,0,0)
                        test.text="운동"
                        param.width =165
                        param.height =230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="음악"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_music,0,0)
                        test.text="음악"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="여행"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_trip,0,0)
                        test.text="여행"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="사교"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_job,0,0)
                        test.text="사교"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="독서"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_read,0,0)
                        test.text="독서"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="요리"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_cook,0,0)
                        test.text="요리"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="사진"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_photo,0,0)
                        test.text="사진"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="게임"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_game,0,0)
                        test.text="게임"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="댄스"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_dance,0,0)
                        test.text="댄스"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="자동차"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_car,0,0)
                        test.text="자동차"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="애완동물"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_pet,0,0)
                        test.text="애완동물"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="공예"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_art,0,0)
                        test.text="공예"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="봉사활동"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_volunteer,0,0)
                        test.text="봉사활동"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    if(data=="스터디그룹"){
                        val test=Button(context)
                        val param=GridLayout.LayoutParams()
                        test.setBackgroundResource(R.drawable.shape_for_circle_button)
                        test.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.xml_study,0,0)
                        test.text="스터디"
                        param.width=165
                        param.height=230
                        param.marginStart=30
                        test.layoutParams=param
                        scrapMainLayout?.addView(test)
                        test.setOnClickListener {
                            val intent = Intent(activity, ClickiconActivity::class.java)
                            intent.putExtra("hobby", data)
                            startActivity(intent)
                        }
                    }
                    db.collection("category").document(data).get().addOnSuccessListener { document2 ->
                        val item2 = document2.toObject(getclubuid::class.java)
                        if (item2?.RoomId != null){
                            for (data2 in item2.RoomId!!) {
                                db.collection("meeting_room").document(data2).get()
                                    .addOnSuccessListener { document3 ->
                                        val item3 = document3.toObject(ClubData::class.java)
                                            clubdata.add(item3!!)
                                            clubdata.sortByDescending { it.upload_time }
                                            notifyDataSetChanged()

                                    }
                            }
                        }
                    }.addOnFailureListener{}
                }
            }

        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view=LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent,false)
            return CustomViewHolder(view)

        }
        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }
        @SuppressLint("CheckResult", "SuspiciousIndentation")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewholder=(holder as CustomViewHolder).itemView
            Glide.with(holder.itemView.context).load(clubdata[position].imageUrl).apply(RequestOptions().circleCrop()).into(viewholder.detailviewitem_imageview_content)
            viewholder.ClubName.text=clubdata[position].title
            viewholder.NumberCount.text= clubdata[position].max.toString()
            viewholder.ClubExplain.text=clubdata[position].info_text
            viewholder.CardView.setOnClickListener{
                var intent= Intent(context, MeetingRoomActivity::class.java)
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