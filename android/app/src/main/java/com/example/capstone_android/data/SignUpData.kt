package com.example.capstone_android.data
import java.util.HashMap
data class SignUpData(var email:String?=null,
                      var password:String?=null,
                      var nickname:String?=null,
                      var uid:String?=null,
                      var birthday:String?=null,
                      var timestamp: Long? = null,
                      var profile_message:String?=null,
                      var interest_array:ArrayList<String>?=ArrayList<String>(),
                      var meeting_room_id_list:ArrayList<String>?=ArrayList<String>(),
                      var edit_time:Long?=null,
                       )