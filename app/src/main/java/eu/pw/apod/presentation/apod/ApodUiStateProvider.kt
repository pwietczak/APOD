package eu.pw.apod.presentation.apod

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.apod.presentation.apod.model.ApodUi

internal class ApodUiStateProvider : PreviewParameterProvider<ApodUiState> {
	override val values: Sequence<ApodUiState> =
		sequenceOf(
			ApodUiState.Loading,
			ApodUiState.Success(
				ApodUi(
					date = "2026-11-14",
					title = "Astronomy Picture of the Day",
					postUrl = "https://science.nasa.gov/apod/",
					imageUi = null,
					credit = "NASA",
					explanation = "This is a sample explanation of the astronomy picture of the day.",
				),
			),
			ApodUiState.Failure,
		)
}