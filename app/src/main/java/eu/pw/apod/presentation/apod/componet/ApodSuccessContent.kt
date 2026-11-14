package eu.pw.apod.presentation.apod.componet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.pw.apod.presentation.apod.model.ApodUi
import eu.pw.apod.presentation.apod.model.ImageUi
import eu.pw.designsystem.ui.theme.ApodTheme

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
		verticalArrangement = Arrangement.spacedBy(16.dp),
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
				style = MaterialTheme.typography.bodyLarge,
				textAlign = TextAlign.Center,
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
					.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			apod.title?.let {
				Text(
					modifier = Modifier.fillMaxWidth(),
					text = apod.title,
					style = MaterialTheme.typography.titleLarge,
					textAlign = TextAlign.Center,
				)
			}

			apod.date?.let {
				Text(
					modifier = Modifier.fillMaxWidth(),
					text = apod.date,
					style = MaterialTheme.typography.bodyMedium,
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
					style = MaterialTheme.typography.bodyLarge,
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
						imageUi =
							ImageUi(
								url = "https://example.com/sample-image.jpg",
								alt = "Sample Image",
							),
						credit = "Sample Credit",
						explanation = "Sample Explanation",
					),
			                  )
		}
	}
}