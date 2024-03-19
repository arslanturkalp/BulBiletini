package com.alparslanturk.biletdevret.data.api

import com.alparslanturk.biletdevret.domain.entities.requests.user.LoginRequest
import com.alparslanturk.biletdevret.domain.entities.responses.user.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ForTokenApiService {

    @POST("api/User/UserLogin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}