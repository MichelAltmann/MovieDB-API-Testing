package com.android.desafiokotlin.database.converter

import androidx.room.TypeConverter

class PopularityConverter {

    @TypeConverter
    fun toString(popularity : Number): Long {
        if (popularity != null){
            return popularity.toLong()
        }
        return 0
    }

    @TypeConverter
    fun toNumber(popularity: Number) : Float {
        if (popularity != null){
            return popularity.toFloat()
        }
        return 0F;
    }

}
