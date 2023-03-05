package com.example.capstone_android.data
import java.util.HashMap

data class ClubData(var category:String?=null,
                    var title:String?=null,
                    var info_text:String?=null,
                    var chatting_id_list:ArrayList<String>?=ArrayList<String>(),
                    var member_list:ArrayList<String>?=ArrayList<String>(),
                    var max:String?=null,
                    var posting_id_list:ArrayList<String>?= ArrayList<String>(),
                    var upload_time: Long? = null,
                    var writer_uid:String?=null,
                    var imageUrl: String? = null,
                    var Uid:String?=null)

