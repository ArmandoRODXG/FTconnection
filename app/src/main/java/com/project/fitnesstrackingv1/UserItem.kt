package com.project.fitnesstrackingv1

data class UserItem(
    val personal_name: String,
    val last_name: String,
    val age: Int,
    val height: Float,
    val weight: Float,
    val email: String,
    val password: String,
    val username: String,
    val gender_id: Int,
    val experience_level_id: Int
)
