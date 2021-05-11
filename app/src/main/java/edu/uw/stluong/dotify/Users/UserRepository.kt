package edu.uw.stluong.dotify.Users

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class UserRepository {
    private val userService = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserService::class.java)

    suspend fun getUser(): User = userService.getUser()
}

interface UserService {
    @GET("echeeUW/codesnippets/master/user_info.json")
    suspend fun getUser(): User
}
