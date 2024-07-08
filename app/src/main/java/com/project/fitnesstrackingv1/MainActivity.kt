package com.project.fitnesstrackingv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.project.fitnesstrackingv1.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var service: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        service = initRetrofitService()

        binding.tex.setOnClickListener {
            registerUser()
        }
        binding.loginXD.setOnClickListener {
            loginUser()
        }

    }

    private fun initRetrofitService(): UserService {
        val baseUrl = "http://10.0.2.2:8000/api/login/"
        return RetrofitClient.createService(baseUrl)
    }

    private fun registerUser(){
        val personal_name = "Juan"
        val last_name = "Pérez"
        val age = 30
        val height = 1.75f
        val weight = 80.5f
        val email = "gtaunivermods@gmail.com"
        val password = "123456789"
        val username = "juanitouwu"
       /* val gender = id
        val training_experience = id*/

        val user = UserItem(personal_name,last_name,age,height,weight,email,password,username,1,1)

        service.registerUser(user).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    showToast("Usuario registrado correctamente!")
                }else{
                    showToast("Error al registrar usuario: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Error con la conexión a laravel: ${t.message}")
            }
        })
    }

    private fun loginUser(){
        val email = "gtaunivermods@gmail.com"
        val password = "123456789"

        val request = LoginRequestUser(email,password)

        service.loginUser(request)
            .enqueue(object : Callback<LoginResponseUser> {
                override fun onResponse(
                    call: Call<LoginResponseUser>,
                    response: Response<LoginResponseUser>
                ) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse?.message != null) {
                            showToast("Error al iniciar sesión: ${loginResponse.message}")
                        } else {
                            showToast("Iniciada la sesión correctamente")
                            Log.d("Datos",response.body().toString())

                            val accessToken = loginResponse?.access_token
                        }
                    } else {
                        val errorResponse = response.errorBody()?.string()
                        showToast("Error al iniciar sesión: $errorResponse")
                    }
                }

                override fun onFailure(call: Call<LoginResponseUser>, t: Throwable) {
                    showToast("Error con la conexión a Laravel: ${t.message}")
                }
            })
    }

    private fun showToast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}
