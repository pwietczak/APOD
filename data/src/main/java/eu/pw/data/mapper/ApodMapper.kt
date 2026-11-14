package eu.pw.data.mapper

import eu.pw.data.datasource.dto.ApodDto
import eu.pw.domain.contract.model.Apod
import eu.pw.domain.contract.model.Image
import javax.inject.Inject

internal interface ApodMapper {
	fun toDomain(dto: ApodDto): Apod
}

internal class ApodMapperImpl
	@Inject
	constructor(
		private val htmlTextSanitizer: HtmlTextSanitizer,
	) : ApodMapper {
		override fun toDomain(dto: ApodDto): Apod =
			Apod(
				date = dto.date,
				title = htmlTextSanitizer.sanitize(dto.title),
				postUrl = dto.postUrl,
				credit = htmlTextSanitizer.sanitize(dto.credit),
				explanation = htmlTextSanitizer.sanitize(dto.explanation),
				image =
					Image(
						url = dto.imageUrl,
						alt = htmlTextSanitizer.sanitize(dto.alt),
					),
			)
	}