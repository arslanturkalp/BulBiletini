package com.alparslanturk.kombineapp.data.api

import com.alparslanturk.kombineapp.domain.entities.requests.user.LoginRequest
import com.alparslanturk.kombineapp.domain.entities.responses.user.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ForTokenApiService {

    @POST("api/User/UserLogin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}