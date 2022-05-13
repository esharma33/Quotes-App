package com.example.quotesapp.models

class post (
    val text: String = "",
    val createdBy: User = User(),
    val createdAt: Long = 0L,
    val likedBy : ArrayList<String> = ArrayList()
)