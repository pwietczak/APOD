package eu.pw.domain.contract.model

data class Apod(
	val date: String?,
	val title: String?,
	val postUrl: String?,
	val credit: String?,
	val explanation: String?,
	val image: Image,
)

data class Image(
	val alt: String?,
	val url: String?,
)