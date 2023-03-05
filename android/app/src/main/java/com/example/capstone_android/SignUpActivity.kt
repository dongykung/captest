package com.example.capstone_android

import android.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_android.data.SignUpData
import com.example.capstone_android.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class SignUpActivity : AppCompatActivity() { // 회원가입 화면
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    val db = Firebase.firestore
    val st = FirebaseStorage.getInstance()
    private val OPEN_GALLERY = 1
    lateinit var storage : FirebaseStorage
    var image : Uri? = null
    var imgName : String? = null
    var selected_profile_img : Int = 0
    companion object {
        const val REQUEST_CODE = 1
        const val UPLOAD_FOLDER = "upload_images/"
    }

    // 회원가입 중 선택된 관심사를 담는 배열 (DB 필드 이름: interest_array)
    var interest_array = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //setContentView(R.layout.activity_signup)

        // 상단 툴바
//        val toolbar = binding.signupToolbar
        setSupportActionBar(binding.signupToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        supportActionBar!!.setTitle("회원가입") // 툴바 제목 설정


        binding.profileImageButton.setOnClickListener(){ // 첨부파일 이미지 버튼 클릭
            println("이미지 버튼 클릭")
            //uploadDialog()
            // 갤러리로 이동하여 이미지 파일을 이미지뷰에 가져옴
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
            startActivityForResult(intent, OPEN_GALLERY)
        }

        // 이메일 중복 검사를 하기위함
        val user_data = db.collection("user")

        // 생년월일 선택 DatePicker
        val datePicker : DatePicker = binding.dpSpinner

        // 가입하기 버튼 클릭
        binding.SignUpButton.setOnClickListener(){
            println("가입하기 버튼 클릭")
            // 체크된 관심사 배열에 세팅
            select_interest_CheckBox()
            if (binding.EmailEditText.getText().toString()==""){     // email을 입력하지 않았을 때
                println("이메일을 입력하지 않음")
                Toast.makeText(
                    this,
                    "이메일을 입력하지않았습니다.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (binding.PWEditText.getText().toString()==""){     // 비밀번호를 입력하지 않았을 때
                println("비밀번호를 입력하지않음")
                Toast.makeText(
                    this,
                    "비밀번호를 입력하지않았습니다.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (binding.PWEditText.getText().toString()!=binding.PWEditText2.getText().toString()){     // 첫번째 적은 비밀번호와 두번째 적은 비밀번호가 같지 않을때
                println("비밀번호가 같지않음")
                Toast.makeText(
                    this,
                    "비밀번호가 같지않습니다.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if(binding.NicknameEditText.getText().toString()==""){    // nickname을 입력하지 않았을 때
                println("닉네임을 입력하지않음")
                Toast.makeText(
                    this,
                    "닉네임을 입력하지않았습니다.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (binding.PWEditText.length()<6){     // 비밀번호 글자수가 6자 미만이면
                println("비밀번호 글자수 6자 미만")
                Toast.makeText(
                    this,
                    "비밀번호를 6자 이상 입력해주세요.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (binding.PWEditText.length()>15){     // 비밀번호 글자수가 15자 초과하면
                println("비밀번호 글자수 15자 초과")
                Toast.makeText(
                    this,
                    "비밀번호를 15자 이하로 입력해주세요.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (binding.NicknameEditText.length()>15){     // 닉네임의 글자수가 15자 초과하면
                println("닉네임 글자수 15자 초과")
                Toast.makeText(
                    this,
                    "닉네임 글자수 15자를 초과하였습니다.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (interest_array.isNullOrEmpty()){
                println("체크된 관심사가 없음")
                Toast.makeText(
                    this,
                    "관심사를 선택해주세요.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                Firebase.auth.createUserWithEmailAndPassword(
                    binding.EmailEditText.getText().toString(),
                    binding.PWEditText.getText().toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        println("회원가입 성공")
                        // DB postings 컬렉션 레퍼런스 가져오기
                        val user = FirebaseAuth.getInstance().currentUser
                        var signdata= SignUpData()
                        signdata.email=binding.EmailEditText.getText().toString()
                        signdata.password=binding.PWEditText.getText().toString()
                        signdata.nickname= binding.NicknameEditText.getText().toString()
                        signdata.uid=Firebase.auth.currentUser?.uid
                        signdata.birthday=datePicker.year.toString()+datePicker.month.toString()+datePicker.dayOfMonth.toString()
                        signdata.timestamp=System.currentTimeMillis()
                        signdata.profile_message=""
                        signdata.interest_array=interest_array
                        signdata.edit_time=System.currentTimeMillis()

                        // DB postings 컬렉션 레퍼런스 가져오기
                        db.collection("user").document(Firebase.auth.currentUser?.uid.toString()).set(signdata)



//                            col.add(itemMap)

                        // 프로필 이미지가 선택되었으면
                        if (selected_profile_img==1){
                            // Firebase Storage로 이미지 전송
                            imgName = "${user?.uid ?: String}"
                            var storageReference =
                                st?.reference?.child("user_profile_image")?.child(imgName!!)

                            storageReference?.putFile(image!!)?.addOnSuccessListener {
                                println("이미지 업로드 성공")
                            }
                        }
                        // 스낵바(토스트) 메시지
                        Toast.makeText(
                            this,
                            "회원가입에 성공하였습니다.",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    }
                    // 이메일 중복 검사
                    else if (binding.EmailEditText.getText().toString()!=""){     // email을 입력했을때
                        user_data.get().addOnSuccessListener {// 이메일 중복 검사를 위해 데이터베이스 읽기
                            for(d in it){
                                if(d["email"]==binding.EmailEditText.getText().toString()){
                                    println("해당 이메일이 이미 존재")
                                    Toast.makeText(
                                        this,
                                        "해당 이메일이 이미 존재합니다.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                    else {
                        println("회원가입 실패 ${it.exception?.message}")
                        Toast.makeText(
                            this,
                            "이메일 형식이 아닙니다.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }


    }

    /*    //액션버튼 메뉴 액션바에 집어 넣기
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.toolbar_menu, menu)
            return true
        }*/
    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                // toolbar의 back키 눌렀을 때 동작
                // 액티비티 뒤로 이동
                finish()
                return true
            }
/*            R.id.action_search -> {
                // 검색 버튼 눌렀을 때
                Toast.makeText(applicationContext, "검색 이벤트 실행", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            R.id.action_share -> {
                // 공유 버튼 눌렀을 때
                Toast.makeText(applicationContext, "공유 이벤트 실행", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }*/
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // 갤러리에서 사진을 선택하면 실행되는 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == OPEN_GALLERY){
                image = data?.data
                try{

                    var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,image)
                    binding.profileImageButton.setImageBitmap(bitmap)
                    selected_profile_img = 1
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    fun select_interest_CheckBox()/*:Array<String> */{
        if(binding.sportsCheckBox.isChecked) {
            interest_array.add("운동")
        }
        if(binding.tripCheckBox.isChecked) {
            interest_array.add("여행")
        }
        if(binding.musicCheckBox.isChecked) {
            interest_array.add("음악")
        }
        if(binding.societyCheckBox.isChecked) {
            interest_array.add("사교")
        }
        if(binding.readCheckBox.isChecked) {
            interest_array.add("독서")
        }
        if(binding.cookCheckBox.isChecked) {
            interest_array.add("요리")
        }
        if(binding.photoCheckBox.isChecked) {
            interest_array.add("사진")
        }
        if(binding.gameCheckBox.isChecked) {
            interest_array.add("게임")
        }
        if(binding.danceCheckBox.isChecked) {
            interest_array.add("댄스")
        }
        if(binding.carCheckBox.isChecked) {
            interest_array.add("차/오토바이")
        }
        if(binding.artCheckBox.isChecked) {
            interest_array.add("공예")
        }
        if(binding.volunteerCheckBox.isChecked) {
            interest_array.add("봉사활동")
        }
        if(binding.studyCheckBox.isChecked) {
            interest_array.add("공부/자기개발")
        }
        println("선택한 관심사 카테고리"+ interest_array)
    }
}