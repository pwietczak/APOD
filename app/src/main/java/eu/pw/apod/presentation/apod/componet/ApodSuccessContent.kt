package eu.pw.apod.presentation.apod.componet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import eu.pw.apod.designsystem.ui.theme.ApodTheme
import eu.pw.apod.presentation.apod.model.ApodUi

@Composable
internal fun ApodSuccessContent(
	modifier: Modifier,
	apod: ApodUi,
) {
	Column(
		modifier =
			modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState()),
		verticalArrangement = Arrangement.spacedBy(ApodTheme.spacing.large),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		ApodImageCard(
			modifier = Modifier,
			apod = apod,
		)

		apod.explanation?.let {
			Text(
				modifier = Modifier.fillMaxWidth(),
				text = apod.explanation,
				style = ApodTheme.typography.bodyMedium,
				textAlign = TextAlign.Justify,
			)
		}
	}
}

@Composable
private fun ApodImageCard(
	modifier: Modifier,
	apod: ApodUi,
) {
	Card(modifier = modifier) {
		Column(
			modifier =
				Modifier
					.fillMaxWidth()
					.padding(ApodTheme.spacing.large),
			verticalArrangement = Arrangement.spacedBy(ApodTheme.spacing.large),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			apod.title?.let {
				Text(
					modifier = Modifier.fillMaxWidth(),
					text = apod.title,
					style = ApodTheme.typography.titleLarge,
					textAlign = TextAlign.Center,
				)
			}

			apod.date?.let {
				Text(
					modifier = Modifier.fillMaxWidth(),
					text = apod.date,
					style = ApodTheme.typography.bodyMedium,
					textAlign = TextAlign.Center,
				)
			}

			ApodImage(
				modifier = Modifier.fillMaxWidth(),
				imageUi = apod.imageUi,
			)

			apod.credit?.let {
				Text(
					modifier = Modifier.fillMaxWidth(),
					text = apod.credit,
					style = ApodTheme.typography.bodyLarge,
					textAlign = TextAlign.Center,
				)
			}
		}
	}
}

@Composable
@Preview
private fun ApodSuccessContentPreview() {
	ApodTheme {
		Surface {
			ApodSuccessContent(
				modifier = Modifier.fillMaxSize(),
				apod =
					ApodUi(
						date = "2024-06-01",
						title = "Sample Title",
						postUrl = "https://example.com",
						imageUi = null,
						credit = "Sample Credit",
						explanation = "Sample Explanation",
					),
			)
		}
	}
}