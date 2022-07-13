package com.example.testtask

import retrofit2.Call
import retrofit2.http.GET

interface ActionsCalls {
    @GET("butto_to_action_config.json")
    fun getActions() : Call<List<Action>>
}