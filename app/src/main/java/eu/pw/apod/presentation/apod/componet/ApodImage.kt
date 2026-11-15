package eu.pw.apod.presentation.apod.componet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import eu.pw.apod.R
import eu.pw.apod.designsystem.ui.theme.ApodTheme
import eu.pw.apod.presentation.apod.model.ImageUi

@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun ApodImage(
	modifier: Modifier,
	imageUi: ImageUi?,
) {
	val loadingDescription = stringResource(R.string.apod_image_loading)
	val imageUrl = imageUi?.url?.takeIf(String::isNotBlank)
	if (imageUrl == null) {
		ImageUnavailableContent(modifier)
		return
	}

	val previewHandler =
		AsyncImagePreviewHandler {
			ColorImage(Color.Red.toArgb())
		}

	CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
		SubcomposeAsyncImage(
			model =
				ImageRequest
					.Builder(LocalContext.current)
					.data(imageUrl)
					.crossfade(true)
					.build(),
			contentDescription = imageUi.alt,
			contentScale = ContentScale.FillWidth,
			modifier = modifier.clip(RoundedCornerShape(ApodTheme.spacing.large)),
			loading = {
				Box(
					modifier = Modifier.fillMaxWidth().aspectRatio(1f),
					contentAlignment = Alignment.Center,
				) {
					CircularProgressIndicator(
						modifier =
							Modifier.semantics {
								contentDescription = loadingDescription
							},
					)
				}
			},
			error = {
				ImageUnavailableContent(Modifier)
			},
		)
	}
}

@Composable
private fun ImageUnavailableContent(modifier: Modifier) {
	Box(
		modifier =
			modifier
				.fillMaxWidth()
				.aspectRatio(
					1f,
				).clip(RoundedCornerShape(ApodTheme.spacing.large)),
		contentAlignment = Alignment.Center,
	) {
		Text(
			text = stringResource(R.string.apod_image_unavailable),
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
		)
	}
}