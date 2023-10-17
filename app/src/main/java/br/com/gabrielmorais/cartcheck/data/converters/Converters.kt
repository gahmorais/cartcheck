package br.com.gabrielmorais.cartcheck.data.converters

import androidx.room.TypeConverter
import br.com.gabrielmorais.cartcheck.data.models.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
  @TypeConverter
  fun fromString(value: String): List<Product> {
    val listType = object : TypeToken<List<Product>>() {}.type
    return Gson().fromJson(value, listType)
  }

  @TypeConverter
  fun listToJson(list: List<Product>): String {
    return Gson().toJson(list)
  }
}