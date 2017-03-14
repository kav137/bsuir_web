package by.bsuir.bsuirweb.core.backend.entity

import com.google.gson.annotations.SerializedName

class Photo {
    @SerializedName("id")
    var id: String = ""

    @SerializedName("name")
    val name: String = ""

    @SerializedName("description")
    val description: String = ""

    @SerializedName("date")
    val date: String = ""
}