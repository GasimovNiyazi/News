package com.niyazi.news.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Source(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: String?,
    @SerializedName("name")
    val name: String?
)