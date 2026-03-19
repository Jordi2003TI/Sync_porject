package com.jorlina.syncapp.CRUD.ITEM

import com.jorlina.syncapp.model.ItemPerfilResponseDTO
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
// http://141.253.193.196:8080/swagger-ui/index.html
interface ItemService {

    @GET("/api/items")
    suspend fun getItem(): retrofit2.Response<List<SyncItem>>

    @GET("/api/item/{id}")
    suspend fun getItemById(
        @Path("id") id: Long
    ): retrofit2.Response<List<SyncItem>>

    @GET("/api/itemsPerfil/{idUser}")
    suspend fun getItemsByUserId(
        @Path("idUser") idUser: Long
    ): retrofit2.Response<List<ItemPerfilResponseDTO>>

    @POST("/api/item")
    suspend fun addItem(
        @Body item: SyncItemRequest
    ): retrofit2.Response<SyncItem>

    @PATCH("/api/item/{id}/titulo")
    suspend fun updateItemTitulo(
        @Path("id") id: Long,
        @Query("titulo") titulo: String
    ): retrofit2.Response<SyncItem>

    @PATCH("/api/item/{id}/descripcion")
    suspend fun updateItemDescripcion(
        @Path("id") id: Long,
        @Query("descripcion") descripcion: String
    ): retrofit2.Response<SyncItem>

    @DELETE("/api/items")
    suspend fun deleteAllItems(): retrofit2.Response<String>

    @DELETE("/api/item/{id}")
    suspend fun deleteItemById(
        @Path("id") id: Long
    ): retrofit2.Response<SyncItem>

    @Multipart
    @POST("/api/item/{id}/image")
    suspend fun uploadImage(
        @Path("id") id: Long,
        @Part image: MultipartBody.Part
    ): retrofit2.Response<String>
}
/*
* @Body  Envía un objeto completo en el cuerpo de la petición (JSON)
* @Path Sustituye valores en la URL
    * @GET("/api/item/{id}")
        suspend fun getItemById(
        @Path("id") id: Long
        )
* @Query Parámetros en la URL tipo ?key=value
    * @PATCH("/api/item/{id}/titulo")
        suspend fun updateItemTitulo(
            @Path("id") id: Long,
            @Query("titulo") titulo: String
        )
* @QueryMap Igual que @Query pero dinámico
    * @GET("/api/items")
        suspend fun getItems(
            @QueryMap filters: Map<String, String>
        )
* @Part (Multipart) Para subir archivos (como tu imagen)
*   @Multipart
    @POST("/api/item/{id}/image")
        suspend fun uploadImage(
            @Path("id") id: Long,
            @Part image: MultipartBody.Part
        )
* @PartMap Varias partes dinámicas
*   @Multipart
    @POST("/upload")
        suspend fun uploadMultiple(
            @PartMap parts: Map<String, RequestBody>
        )
* @Field Para enviar datos tipo formulario (x-www-form-urlencoded)
*   @FormUrlEncoded
    @POST("/login")
        suspend fun login(
            @Field("user") user: String,
            @Field("password") password: String
        )
* @FieldMap Igual que Field pero dinámico
*   @FormUrlEncoded
    @POST("/login")
        suspend fun login(
            @FieldMap fields: Map<String, String>
    )
* @Header Añadir headers manuales
*   @GET("/api/items")
        suspend fun getItems(
            @Header("Authorization") token: String
        )
* @HeaderMap Headers dinámicos
*   @GET("/api/items")
        suspend fun getItems(
            @HeaderMap headers: Map<String, String>
        )
* @Url Pasar URL completa dinámicamente
*   @GET
    suspend fun getFromUrl(
        @Url url: String
    )
* @FormUrlEncoded Se usa junto a @Field
    * @FormUrlEncoded
      @POST("/form")
* @Multipart Para archivos (como ya usas)
    *@Multipart
     @POST("/upload")
* */