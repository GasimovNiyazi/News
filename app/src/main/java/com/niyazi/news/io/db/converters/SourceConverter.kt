package com.niyazi.news.io.db.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.niyazi.news.model.Source
import javax.inject.Inject

@ProvidedTypeConverter
class SourceConverter @Inject constructor(private val gson: Gson) {

    @TypeConverter
    fun fromSource(source: Source): String = gson.toJson(source)

    @TypeConverter
    fun toSource(json: String): Source {
        val objectType = object : TypeToken<Source>() {}.type

        return try {
            gson.fromJson(json, objectType)
        } catch (error: Throwable) {
            error.printStackTrace()
            Source(null, null)
        }
    }

}