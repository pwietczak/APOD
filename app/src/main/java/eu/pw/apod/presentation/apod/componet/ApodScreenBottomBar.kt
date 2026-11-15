package eu.pw.apod.presentation.apod.componet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.pw.apod.R
import eu.pw.apod.designsystem.component.PrimaryButton
import eu.pw.apod.presentation.apod.ApodUiState

@Composable
internal fun ApodScreenBottomBar(
	modifier: Modifier = Modifier,
	uiState: ApodUiState,
	reloadAction: () -> Unit,
) {
	val isLoading = uiState is ApodUiState.Loading
	val buttonTextRes =
		when (uiState) {
			is ApodUiState.Success -> R.string.apod_button_reload
			ApodUiState.Failure, is ApodUiState.Loading -> R.string.apod_button_retry
		}

	PrimaryButton(
		onClick = reloadAction,
		enabled = !isLoading,
		text = stringResource(buttonTextRes),
		modifier = modifier.fillMaxWidth(),
	)
}