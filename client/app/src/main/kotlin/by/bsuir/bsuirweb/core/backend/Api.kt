package by.bsuir.bsuirweb.core.backend

import by.bsuir.bsuirweb.core.backend.ApiContract
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

val Api: ApiContract by lazy {
    val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://172.17.2.249:3001")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    retrofitBuilder.create(ApiContract::class.java)
}
