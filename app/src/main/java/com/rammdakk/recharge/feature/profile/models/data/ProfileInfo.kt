package com.rammdakk.recharge.feature.profile.models.data

import android.util.Log
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@JsonIgnoreProperties(ignoreUnknown = true)
data class ProfileInfo(
    @JsonProperty("name")
    @get:JsonProperty("name")
    val firstName: String? = "",
    @JsonProperty("surname")
    @get:JsonProperty("surname")
    val secondName: String? = "",
    @JsonProperty("phoneNumber")
    @get:JsonProperty("phoneNumber")
    val phone: String? = "",
    @JsonProperty("email")
    @get:JsonProperty("email")
    val email: String? = "",
    @JsonSerialize(using = DateSerializer::class)
    @JsonProperty("birthDate")
    @get:JsonProperty("birthDate")
    val birthDay: Date? = null,
    @JsonProperty("gender")
    @get:JsonProperty("gender")
    val gender: Gender? = null,
    @JsonProperty("city")
    @get:JsonProperty("city")
    val city: String? = null,
    @JsonProperty("imageUrl")
    @get:JsonProperty("imageUrl")
    val imageUrl: String? = null
)


class DateSerializer @JvmOverloads constructor(t: Class<Date?>? = null) :
    StdSerializer<Date>(t) {
    override fun serialize(
        value: Date, jgen: JsonGenerator, provider: SerializerProvider
    ) {
        Log.d("Ramil", "ser")
        jgen.writeString(formatDateToCustomFormat(value))
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

        fun formatDateToCustomFormat(date: Date): String {
            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("UTC") // Ensure UTC timezone
            return formatter.format(date)
        }
    }
}

enum class Gender { MALE, FEMALE }