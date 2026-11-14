package eu.pw.data.datasource

import com.squareup.moshi.Moshi
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ApodRemoteDataSourceTest {
	@Test
	fun `getApod parses image response including numeric post id`() =
		runTest {
			val client =
				clientFor(
					"""
					{
					  "date": "2026-11-14",
					  "post_id": 1511402,
					  "title": "The Cosmos",
					  "permalink": "https://science.nasa.gov/image-article/apod-2026-november-14/",
					  "media_type": "image",
					  "hdurl": "https://example.com/image.jpg",
					  "alt": "Stars",
					  "credit": "NASA",
					  "explanation": "A view of the cosmos."
					}
					""".trimIndent(),
				)

			try {
				val apod = ApodRemoteDataSource(client, Moshi.Builder().build()).getApod("261114")

				assertEquals("2026-11-14", apod.date)
				assertEquals("The Cosmos", apod.title)
				assertEquals("https://science.nasa.gov/image-article/apod-2026-november-14/", apod.postUrl)
				assertEquals("image", apod.mediaType)
				assertEquals("https://example.com/image.jpg", apod.imageUrl)
			} finally {
				client.close()
			}
		}

	@Test
	fun `getApod parses video response without image url`() =
		runTest {
			val client =
				clientFor(
					"""
					{
					  "date": "2026-11-14",
					  "title": "A Solar Eclipse",
					  "media_type": "video",
					  "url": "https://example.com/video",
					  "explanation": "A video of an eclipse."
					}
					""".trimIndent(),
				)

			try {
				val apod = ApodRemoteDataSource(client, Moshi.Builder().build()).getApod("261114")

				assertEquals("video", apod.mediaType)
				assertNull(apod.imageUrl)
			} finally {
				client.close()
			}
		}

	@Test
	fun `getApod rejects a response missing required content`() =
		runTest {
			val client = clientFor("""{"media_type":"image"}""")

			try {
				val exception =
					runCatching {
						ApodRemoteDataSource(client, Moshi.Builder().build()).getApod("261114")
					}.exceptionOrNull()
				assertTrue(exception is IllegalArgumentException)
				assertTrue(exception?.message.orEmpty().contains("date or title"))
			} finally {
				client.close()
			}
		}

	@Test
	fun `getApod surfaces HTTP error responses`() =
		runTest {
			val client =
				clientFor(
					body = """{"message":"Unavailable"}""",
					status = HttpStatusCode.ServiceUnavailable,
				)

			try {
				val exception =
					runCatching {
						ApodRemoteDataSource(client, Moshi.Builder().build()).getApod("261114")
					}.exceptionOrNull()
				assertTrue(exception is ServerResponseException)
			} finally {
				client.close()
			}
		}

	private fun clientFor(
		body: String,
		status: HttpStatusCode = HttpStatusCode.OK,
	): HttpClient =
		HttpClient(MockEngine) {
			expectSuccess = true
			engine {
				addHandler {
					respond(
						content = body,
						status = status,
						headers = headersOf(HttpHeaders.ContentType, "application/json"),
					)
				}
			}
		}
}