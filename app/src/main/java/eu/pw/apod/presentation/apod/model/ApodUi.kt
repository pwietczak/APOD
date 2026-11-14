package eu.pw.apod.presentation.apod.model

import eu.pw.domain.contract.model.Apod
import javax.inject.Inject

internal class ApodUi(
	val date: String?,
	val title: String?,
	val postUrl: String?,
	val imageUi: ImageUi?,
	val credit: String?,
	val explanation: String?,
)

internal data class ImageUi(
	val url: String,
	val alt: String?,
)

internal class ApodUiMapper
	@Inject
	constructor() {
		fun map(apod: Apod): ApodUi =
			ApodUi(
				date = apod.date,
				title = apod.title,
				postUrl = apod.postUrl,
				imageUi =
					apod.image.url?.let {
						ImageUi(
							url = it,
							alt = apod.image.alt,
						)
					},
				credit = apod.credit,
				explanation = apod.explanation,
			)
	}