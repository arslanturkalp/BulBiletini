package com.alparslanturk.kombineapp.domain.repository

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.LoginRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.kombineapp.domain.entities.responses.user.deleteuser.UserDeleteResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.forgotpassword.ForgotPasswordResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.register.RegisterResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.verificationcode.VerificationCodeResponse

interface Repository {

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse>

    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse>

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Result<ForgotPasswordResponse>

    suspend fun getVerificationCode(email: String): Result<VerificationCodeResponse>

    suspend fun deleteUser(userDeleteRequest: UserDeleteRequest): Result<UserDeleteResponse>
}