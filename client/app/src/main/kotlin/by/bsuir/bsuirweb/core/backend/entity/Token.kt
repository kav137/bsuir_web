package by.bsuir.bsuirweb.core.backend.entity

import com.google.gson.annotations.SerializedName

class Token {
    @SerializedName("token")
    lateinit var value : String
}