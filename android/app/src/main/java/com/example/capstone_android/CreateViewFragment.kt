package com.example.capstone_android

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstone_android.data.ClubData
import com.example.capstone_android.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_create.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateViewFragment: Fragment() {
    lateinit var storage: FirebaseStorage
    lateinit var db : FirebaseFirestore
    var photoUri: Uri?=null
    lateinit var hobby:String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= LayoutInflater.from(activity).inflate(R.layout.fragment_create,container,false)
        storage = Firebase.storage
        db= Firebase.firestore
        var mActivity = activity as CreateActivity
        view.changehobby.setOnClickListener{
            val orderBottomDialogFragment: OrderBottomDialogFragment = OrderBottomDialogFragment {
                when (it) {
                    "운동"->{hobby="운동"
                    view.changehobby.setImageResource(R.drawable.icon_sports)}
                    "여행"->{hobby="여행"
                        view.changehobby.setImageResource(R.drawable.icon_trip)}
                    "음악"->{hobby="음악"
                        view.changehobby.setImageResource(R.drawable.icon_music)}
                    "사교"->{hobby="사교"
                        view.changehobby.setImageResource(R.drawable.icon_job)}
                    "독서"->{hobby="독서"
                        view.changehobby.setImageResource(R.drawable.icon_book)}
                    "요리"->{hobby="요리"
                        view.changehobby.setImageResource(R.drawable.icon_cook)}
                    "사진"->{hobby="사진"
                        view.changehobby.setImageResource(R.drawable.icon_photo)}
                    "게임"->{hobby="게임"
                        view.changehobby.setImageResource(R.drawable.icon_game)}
                    "댄스"->{hobby="댄스"
                        view.changehobby.setImageResource(R.drawable.icon_dance)}
                    "자동차"->{hobby="자동차"
                        view.changehobby.setImageResource(R.drawable.icon_car)}
                    "애완동물"->{hobby="애완동물"
                        view.changehobby.setImageResource(R.drawable.icon_pet)}
                    "공예"->{hobby="공예"
                        view.changehobby.setImageResource(R.drawable.icon_art)}
                    "봉사활동"->{hobby="봉사활동"
                        view.changehobby.setImageResource(R.drawable.icon_volunteer)}
                    "스터디그룹"->{hobby="스터디그룹"
                        view.changehobby.setImageResource(R.drawable.icon_study)}
                }
            }
            orderBottomDialogFragment.show(mActivity.supportFragmentManager, orderBottomDialogFragment.tag)
        }




        val filterActivityLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if(it.resultCode == AppCompatActivity.RESULT_OK && it.data !=null) {
                    photoUri = it.data?.data
                    try {
                        photoUri?.let {
                            if(Build.VERSION.SDK_INT < 28) {
                                val bitmap = MediaStore.Images.Media.getBitmap(
                                    mActivity.contentResolver,
                                    photoUri
                                )
                                view.clubprofile.setImageBitmap(bitmap)
                            } else {
                                val source = ImageDecoder.createSource(mActivity.contentResolver, photoUri!!)
                                val bitmap = ImageDecoder.decodeBitmap(source)
                                view.clubprofile.setImageBitmap(bitmap)
                            }
                        }


                    }catch(e:Exception) {
                        e.printStackTrace()
                    }
                } else if(it.resultCode == AppCompatActivity.RESULT_CANCELED){
                    Toast.makeText(mActivity, "사진 선택 취소", Toast.LENGTH_LONG).show();
                }else{
                    Log.d("ActivityResult","something wrong")
                }
            }

        view.clubprofile.setOnClickListener{
            val intent=Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            filterActivityLauncher.launch(intent)
        }
        view.CreateBtn.setOnClickListener{
            uploadContent(hobby,view)
        }
        return view
    }
    @SuppressLint("SimpleDateFormat")
    fun uploadContent(hobby: String, view: View){
        val s1:String= Firebase.auth.currentUser?.uid.toString()
        val s2:String=SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val makeuid=s1.plus(s2)
        db.collection("category").document(hobby).update("RoomId",FieldValue.arrayUnion(makeuid)).addOnFailureListener{
            val data= hashMapOf("RoomId" to ArrayList<String>())
            println(data)
            println("테스트")
            db.collection("category").document(hobby).set(data)
            db.collection("category").document(hobby).update("RoomId",FieldValue.arrayUnion(makeuid))
        }

        val clubprofileimagename=makeuid
        val storageRef=storage.reference.child("meeting_info").child(clubprofileimagename)

        storageRef.putFile(photoUri!!).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                val clubdata = ClubData()
                clubdata.category=hobby
                clubdata.imageUrl=uri.toString()
                clubdata.title = view.club.text.toString()
                clubdata.info_text = view.explainclub2.text.toString()
                clubdata.max = (view.maxnumber.text.toString())
                clubdata.upload_time = System.currentTimeMillis()
                clubdata.writer_uid = Firebase.auth.currentUser?.uid.toString()
                clubdata.Uid=makeuid
                db.collection("meeting_room").document(makeuid).set(clubdata).addOnSuccessListener{
                    db.collection("meeting_room").document(makeuid).update("member_list",FieldValue.arrayUnion(Firebase.auth.currentUser?.uid.toString())).addOnSuccessListener{
                        db.collection("user").document(Firebase.auth.currentUser?.uid.toString()).update("meeting_room_id_list",FieldValue.arrayUnion(makeuid)).addOnSuccessListener{
                            activity?.finish()
                            println("모임만들기 성공")
                        }
                    }
                }
            }
        }

    }
}