package com.example.smo1.data.api

import com.example.smo1.data.model.CuttingRequest
import com.example.smo1.data.model.GetOrder
import com.example.smo1.data.model.Machine
import com.example.smo1.data.model.SetOrder
import com.example.smo1.data.model.WorkerRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @POST("/setbundle")
    suspend fun setBundle(@Body request: CuttingRequest): Response<Unit>

    @POST("/performbundle")
    suspend fun performBundle(@Body request: WorkerRequest): Response<Unit>

    @GET("/getorders")  // Ensure Spring Boot exposes this endpoint
    fun getOrders(): Call<List<GetOrder>>

    @POST("/setorders")
    fun addOrder(@Body order: SetOrder): Call<SetOrder>


    @GET("/worker-names")
    fun getWorkerNames(): Call<List<String>>

    @GET("/machines")  // Replace with your actual endpoint
    fun getMachines(): Call<List<Machine>>


}