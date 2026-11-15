package eu.pw.apod.widget.presentation

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ApodWidgetUiState {
	data class Presenting(
		val title: String,
	) : ApodWidgetUiState

	data object Loading : ApodWidgetUiState
}