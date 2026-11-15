package eu.pw.apod.presentation.apod

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.pw.apod.R
import eu.pw.apod.designsystem.ui.theme.ApodTheme
import eu.pw.apod.presentation.apod.componet.ApodScreenBottomBar
import eu.pw.apod.presentation.apod.componet.ApodSuccessContent

@Composable
internal fun ApodScreen(
	modifier: Modifier,
	viewModel: ApodViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	ApodScreenContent(
		modifier = modifier.padding(ApodTheme.spacing.large),
		uiState = uiState,
		reloadAction = viewModel::loadApod,
	)
}

@Composable
private fun ApodScreenContent(
	modifier: Modifier,
	uiState: ApodUiState,
	reloadAction: () -> Unit,
) {
	Scaffold(
		modifier = modifier,
		bottomBar = {
			ApodScreenBottomBar(
				uiState = uiState,
				reloadAction = reloadAction,
			)
		},
	) { paddingValues ->
		when (uiState) {
			is ApodUiState.Loading -> {
				LoadingContent(modifier = Modifier.padding(paddingValues))
			}

			is ApodUiState.Success -> {
				ApodSuccessContent(modifier = Modifier.padding(paddingValues), apod = uiState.apod)
			}

			ApodUiState.Failure -> {
				ErrorContent(
					modifier = Modifier.padding(paddingValues),
				)
			}
		}
	}
}

@Composable
private fun ErrorContent(modifier: Modifier) {
	Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Text(text = stringResource(R.string.apod_load_error), style = MaterialTheme.typography.bodyMedium)
	}
}

@Composable
private fun LoadingContent(modifier: Modifier) {
	Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		CircularProgressIndicator()
	}
}

@PreviewLightDark
@Composable
private fun ApodScreenPreview(
	@PreviewParameter(ApodUiStateProvider::class) uiState: ApodUiState,
) {
	ApodTheme {
		Surface {
			ApodScreenContent(
				modifier = Modifier.fillMaxSize(),
				uiState = uiState,
				reloadAction = {},
			)
		}
	}
}