package by.bsuir.bsuirweb.core.backend

import by.bsuir.bsuirweb.core.backend.entity.Token
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiContract {
    @FormUrlEncoded
    @POST("/login")
    fun authorize(@Field("username") name: String, @Field("password") password: String) : Observable<Token>

    @Multipart
    @POST
    fun uploadPhoto(@Part("photo") file: RequestBody, @Part("description") description: String)
}