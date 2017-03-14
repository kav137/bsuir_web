package by.bsuir.bsuirweb.core.backend

import android.content.Context
import by.bsuir.bsuirweb.core.backend.ApiContract
import by.bsuir.bsuirweb.core.media.Files
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import okhttp3.RequestBody



val Api: ApiContract by lazy {

    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://192.168.0.103:3001")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    retrofitBuilder.create(ApiContract::class.java)
}

fun ApiContract.uploadPhoto(context: Context, file: File, tokenValue: String): Observable<ResponseBody> {
    val requestFile =
            RequestBody.create(
                         MediaType.parse(context.contentResolver.getType(Files.getUri(context, file))),
                         file
             );

    val body =
            MultipartBody.Part.createFormData("photo", file.getName(), requestFile)

    val token = MultipartBody.Part.createFormData("token", tokenValue)

    val descriptionString = "hello, this is description speaking"

    val description = RequestBody.create(
            okhttp3.MultipartBody.FORM, descriptionString)

    return uploadPhotoMultipart(description, body, token)
}
