package com.project.fitnesstrackingv1

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class ResponseUser(
    val personal_name: String,
    val last_name: String
)

data class LoginRequestUser(
    val email: String,
    val password: String
)

data class LoginResponseUser(
    val access_token: String?,
    val token_type: String?,
    val user: User,
    val message: String?
)

data class User(
    val id: Int,
    val personal_name: String,
    val last_name: String,
    val age: Int,
    val height: Double,
    val weight: Double,
    val email: String,
    val email_verified_at: String,
    val username: String,
    val gender_id: Int,
    val experience_level_id: Int,
)

interface UserService {
    @GET("/api/users")
    fun getUsers(): Call<List<UserItem>>

    @POST("/api/register")
    fun registerUser(@Body user: UserItem): Call<Void>

    @POST("/api/login")
    fun loginUser(@Body loginRequestUser: LoginRequestUser): Call<LoginResponseUser>
}