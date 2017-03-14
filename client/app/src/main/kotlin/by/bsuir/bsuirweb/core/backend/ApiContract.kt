package by.bsuir.bsuirweb.core.backend

import by.bsuir.bsuirweb.core.backend.entity.Photos
import by.bsuir.bsuirweb.core.backend.entity.Token
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiContract {
    @FormUrlEncoded
    @POST("/login")
    fun authorize(@Field("username") name: String, @Field("password") password: String) : Observable<Token>

    @Multipart
    @POST("/api/photos/upload")
    fun uploadPhotoMultipart(@Part("description") description: RequestBody,
                             @Part file: MultipartBody.Part,
                             @Part token: MultipartBody.Part): Observable<ResponseBody>

    @GET("/api/photos/list")
    fun photos(@Header("Authorization") token: String) : Observable<Photos>

}