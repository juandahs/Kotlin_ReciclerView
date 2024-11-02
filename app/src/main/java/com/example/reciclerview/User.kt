package com.example.reciclerview

data class User(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String,
    var avatar: String
){
    fun getFullName():String = "$first_name $last_name"
}
