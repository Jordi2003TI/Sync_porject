package com.jorlina.syncapp.CRUD.ITEM

import com.jorlina.syncapp.model.SyncItem
import com.jorlina.syncapp.model.SyncItemRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemService {

    @GET("/api/items")
    suspend fun getItem(): retrofit2.Response<List<SyncItem>>

    @GET("/api/item/{id}")
    suspend fun getItemById(
        @Path("id") id: Long
    ): retrofit2.Response<List<SyncItem>>

    @POST("/api/item")
    suspend fun addItem(
        @Body item: SyncItemRequest
    ): retrofit2.Response<String>

    @PATCH("/api/item/{id}/titulo")
    suspend fun updateItemTitulo(
        @Path("id") id: Long,
        @Query("titulo") titulo: String
    ): retrofit2.Response<String>

    @PATCH("/api/item/{id}/descripcion")
    suspend fun updateItemDescripcion(
        @Path("id") id: Long,
        @Query("descripcion") descripcion: String
    ): retrofit2.Response<String>

    @DELETE("/api/items")
    suspend fun deleteAllItems(): retrofit2.Response<String>

    @DELETE("/api/item/{id}")
    suspend fun deleteItemById(
        @Path("id") id: Long
    ): retrofit2.Response<String>

    @Multipart
    @POST("/api/item/{id}/image")
    suspend fun uploadImage(
        @Path("id") id: Long,
        @Part image: MultipartBody.Part
    ): retrofit2.Response<String>
}
