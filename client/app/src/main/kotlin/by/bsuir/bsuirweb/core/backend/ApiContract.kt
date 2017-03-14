package by.bsuir.bsuirweb.core.backend

import by.bsuir.bsuirweb.core.backend.entity.Token
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiContract {
    @FormUrlEncoded
    @POST("/login")
    fun authorize(@Field("username") name: String, @Field("password") password: String) : Observable<Token>
}