package eu.pw.data.datasource

import com.squareup.moshi.Moshi
import eu.pw.data.datasource.dto.ApodDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

internal interface ApodDataSource {
	suspend fun getApod(date: String): ApodDto
}

internal class ApodRemoteDataSource
	@Inject
	constructor(
		private val httpClient: HttpClient,
		moshi: Moshi,
	) : ApodDataSource {
		private val adapter = moshi.adapter(ApodDto::class.java)

		private companion object {
			const val API_BASE_URL = "https://science.nasa.gov/wp-json/wp/v2/apod-basic/"
		}

		override suspend fun getApod(date: String): ApodDto {
			val responseBody = httpClient.get("$API_BASE_URL$date").bodyAsText()

			val apod =
				adapter.fromJson(responseBody)
					?: throw IllegalStateException("Failed to parse Apod response")
			require(!apod.date.isNullOrBlank() && !apod.title.isNullOrBlank()) {
				"Apod response is missing its date or title"
			}
			return apod
		}
	}