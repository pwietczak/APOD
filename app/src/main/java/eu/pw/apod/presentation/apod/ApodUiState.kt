package eu.pw.apod.presentation.apod

import eu.pw.apod.presentation.apod.model.ApodUi

internal sealed interface ApodUiState {
	data object Loading : ApodUiState

	data class Success(
		val apod: ApodUi,
	) : ApodUiState

	data object Failure : ApodUiState
}