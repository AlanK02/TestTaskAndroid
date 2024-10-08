package com.alan.database.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.alan.core.models.Address
import com.alan.core.models.Button
import com.alan.core.models.Experience
import com.alan.core.models.Salary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converter() {

    private val gson = Gson()

    @TypeConverter
    fun fromButtonJson(json: String): Button? {
        return gson.fromJson(json, object : TypeToken<Button>() {}.type)
    }

    @TypeConverter
    fun toButtonJson(button: Button?): String? {
        return gson.toJson(button)
    }

    @TypeConverter
    fun fromAddressJson(json: String): Address {
        return gson.fromJson(json, object : TypeToken<Address>() {}.type)
    }

    @TypeConverter
    fun toAddressJsom(address: Address?): String? {
        return gson.toJson(address) ?: ""
    }

    @TypeConverter
    fun fromExperienceJson(json: String): Experience {
        return gson.fromJson(json, object : TypeToken<Experience>() {}.type)
    }

    @TypeConverter
    fun toExperienceJson(experience: Experience?): String? {
        return gson.toJson(experience) ?: ""
    }

    @TypeConverter
    fun fromSalaryJson(json: String): Salary {
        return gson.fromJson(json, object : TypeToken<Salary>() {}.type)
    }

    @TypeConverter
    fun toSalaryJson(salary: Salary?): String? {
        return gson.toJson(salary) ?: ""
    }

    @TypeConverter
    fun fromListStringJson(json: String): List<String> {
        return gson.fromJson(json, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun toListStringJson(list: List<String>?): String? {
        return gson.toJson(list)
    }
}