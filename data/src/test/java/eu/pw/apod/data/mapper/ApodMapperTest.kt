package eu.pw.apod.data.mapper

import eu.pw.apod.data.datasource.dto.ApodDto
import org.junit.Assert.assertEquals
import org.junit.Test

class ApodMapperTest {
	private val mapper = ApodMapperImpl(HtmlTextSanitizerImpl())

	@Test
	fun `toDomain sanitizes text fields and maps credit and explanation`() {
		val dto =
			ApodDto(
				date = "2026-11-14",
				title = "<em>Apollo</em> <a>12</a>",
				postUrl = "https://science.nasa.gov/image-article/apod-2026-november-14/",
				mediaType = "image",
				imageUrl = "https://example.com/image.jpg",
				alt = "Astronaut <strong>on the Moon</strong>",
				credit = "<a>Apollo 12</a> &amp; Alan Bean",
				explanation = "<strong>Explanation:</strong> Visit <a>Surveyor 3</a> on the Moon.",
			)

		val apod = mapper.toDomain(dto)

		assertEquals("2026-11-14", apod.date)
		assertEquals("Apollo 12", apod.title)
		assertEquals("https://science.nasa.gov/image-article/apod-2026-november-14/", apod.postUrl)
		assertEquals("Apollo 12 & Alan Bean", apod.credit)
		assertEquals("Explanation: Visit Surveyor 3 on the Moon.", apod.explanation)
		assertEquals("Astronaut on the Moon", apod.image.alt)
		assertEquals("https://example.com/image.jpg", apod.image.url)
	}

	@Test
	fun `toDomain preserves nullable text fields`() {
		val dto =
			ApodDto(
				date = null,
				title = null,
				postUrl = null,
				mediaType = null,
				imageUrl = null,
				alt = null,
				credit = null,
				explanation = null,
			)

		val apod = mapper.toDomain(dto)

		assertEquals(null, apod.title)
		assertEquals(null, apod.postUrl)
		assertEquals(null, apod.credit)
		assertEquals(null, apod.explanation)
		assertEquals(null, apod.image.alt)
	}
}