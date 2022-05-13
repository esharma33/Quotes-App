package com.example.quotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quotesapp.daos.PostDao
import kotlinx.android.synthetic.main.activity_creat_post.*

class CreatPost : AppCompatActivity() {
    private lateinit var postDao : PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_post)
        postDao = PostDao()
        postButton.setOnClickListener{
            val input = postInput.text.toString().trim()
            if (input.isNotEmpty()){
                postDao.addPost(input)
                finish()

            }
        }
    }
}