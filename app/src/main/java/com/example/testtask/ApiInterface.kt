package com.example.testtask

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    companion object {
        private const val BASE_URL = "https://s3-us-west-2.amazonaws.com/androidexam/"

        fun create(): ActionsCalls {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ActionsCalls::class.java)

        }
    }
}