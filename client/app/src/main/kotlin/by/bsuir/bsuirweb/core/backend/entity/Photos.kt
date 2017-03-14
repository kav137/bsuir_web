package by.bsuir.bsuirweb.core.backend.entity

import com.google.gson.annotations.SerializedName

class Photos {
    @SerializedName("result")
    lateinit var photos: List<Photo>
}