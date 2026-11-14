package eu.pw.data.datasource.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ApodDto(
	val date: String? = null,
	val title: String? = null,
	@field:Json(name = "permalink")
	val postUrl: String? = null,
	@field:Json(name = "media_type")
	val mediaType: String? = null,
	@field:Json(name = "hdurl")
	val imageUrl: String? = null,
	val alt: String? = null,
	val credit: String? = null,
	val explanation: String? = null,
)