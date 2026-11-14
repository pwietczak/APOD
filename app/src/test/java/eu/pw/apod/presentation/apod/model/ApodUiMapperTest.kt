package eu.pw.apod.presentation.apod.model

import eu.pw.domain.contract.model.Apod
import eu.pw.domain.contract.model.Image
import org.junit.Assert.assertEquals
import org.junit.Test

class ApodUiMapperTest {
	private val mapper = ApodUiMapper()

	@Test
	fun `map passes post url through to UI model`() {
		val apod =
			Apod(
				date = "2026-11-14",
				title = "The Cosmos",
				postUrl = "https://science.nasa.gov/image-article/apod-2026-november-14/",
				credit = "NASA",
				explanation = "A view of the cosmos.",
				image = Image(alt = "Stars", url = "https://example.com/image.jpg"),
			)

		val apodUi = mapper.map(apod)

		assertEquals("https://science.nasa.gov/image-article/apod-2026-november-14/", apodUi.postUrl)
	}
}