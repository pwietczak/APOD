package eu.pw.apod.presentation.apod.componet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.pw.apod.R
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

	Button(
		onClick = reloadAction,
		enabled = !isLoading,
		modifier = modifier.fillMaxWidth().padding(top = 16.dp),
	) {
		Text(
			text = stringResource(buttonTextRes),
			style = MaterialTheme.typography.labelLarge,
		)
	}
}