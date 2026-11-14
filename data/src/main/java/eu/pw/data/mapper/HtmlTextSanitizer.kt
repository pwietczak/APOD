package eu.pw.data.mapper

import org.jsoup.Jsoup
import javax.inject.Inject

internal interface HtmlTextSanitizer {
	fun sanitize(value: String?): String?
}

internal class HtmlTextSanitizerImpl
	@Inject
	constructor() : HtmlTextSanitizer {
		private val whitespace = Regex("\\s+")

		override fun sanitize(value: String?): String? =
			value?.let {
				val body = Jsoup.parseBodyFragment(it).body()
				body
					.text()
					.replace('\u00A0', ' ')
					.replace(whitespace, " ")
					.trim()
			}
	}