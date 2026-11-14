package eu.pw.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test

class HtmlTextSanitizerTest {
	private val sanitizer = HtmlTextSanitizerImpl()

	@Test
	fun `sanitize removes markup but preserves linked text and decodes entities`() {
		val html =
			"<strong>Explanation:</strong> Put on " +
				"<a href=\"https://example.com\">red/blue glasses</a> &amp; look<br>again"

		assertEquals("Explanation: Put on red/blue glasses & look again", sanitizer.sanitize(html))
	}

	@Test
	fun `sanitize preserves linked text`() {
		assertEquals(
			"Surveyor 3 had landed at the site",
			sanitizer.sanitize("Surveyor 3 had landed <a>at</a> the site"),
		)
	}

	@Test
	fun `sanitize preserves null`() {
		assertEquals(null, sanitizer.sanitize(null))
	}
}