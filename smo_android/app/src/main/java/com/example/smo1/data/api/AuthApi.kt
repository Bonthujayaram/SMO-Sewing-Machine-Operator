package com.example.smo1.data.api

import com.example.smo1.data.model.LoginRequest
import com.example.smo1.data.model.RoleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<RoleResponse>
}