package com.alparslanturk.kombineapp.data.repository

import com.alparslanturk.kombineapp.data.api.ApiService
import com.alparslanturk.kombineapp.data.api.ForTokenApiService
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
import com.alparslanturk.kombineapp.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService, private val forTokenApiService: ForTokenApiService) : Repository {
    override suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        val response = forTokenApiService.login(loginRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        val response = apiService.register(registerRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Result<ForgotPasswordResponse> {
        val response = apiService.forgotPassword(forgotPasswordRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getVerificationCode(email: String): Result<VerificationCodeResponse> {
        val response = apiService.getVerificationCode(email)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun deleteUser(userDeleteRequest: UserDeleteRequest): Result<UserDeleteResponse> {
        val response = apiService.deleteUser(userDeleteRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }
}