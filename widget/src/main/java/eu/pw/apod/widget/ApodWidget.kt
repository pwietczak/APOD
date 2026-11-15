package eu.pw.apod.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import eu.pw.apod.widget.presentation.ApodWidgetUiState

internal class ApodWidget : GlanceAppWidget() {
	override suspend fun provideGlance(
		context: Context,
		id: GlanceId,
	) {
		provideContent {
			GlanceTheme {
				val uiState = ApodWidgetUiState.Presenting(title = "Preview Title")
				GlanceTheme {
					ApodWidgetContent(modifier = GlanceModifier, uiState = uiState)
				}
			}
		}
	}

	override suspend fun providePreview(
		context: Context,
		widgetCategory: Int,
	) {
		provideContent {
			val uiState = ApodWidgetUiState.Presenting(title = "Preview Title")
			GlanceTheme {
				ApodWidgetContent(modifier = GlanceModifier, uiState = uiState)
			}
		}
	}
}

@Composable
private fun ApodWidgetContent(
	modifier: GlanceModifier,
	uiState: ApodWidgetUiState,
) {
	Box(
		modifier =
			modifier
				.appWidgetBackground()
				.background(ImageProvider(R.drawable.widget_background))
				.wrapContentSize()
				.padding(16.dp),
		contentAlignment = Alignment.Center,
	) {
		when (uiState) {
			ApodWidgetUiState.Loading -> ApodWidgetLoading()
			is ApodWidgetUiState.Presenting -> ApodPresenting(uiState)
		}
	}
}

@Composable
fun ApodPresenting(uiState: ApodWidgetUiState.Presenting) {
	Text(
		text = uiState.title,
		style = TextStyle(color = GlanceTheme.colors.onBackground),
	)
}

@Composable
fun ApodWidgetLoading() {
	CircularProgressIndicator(
		modifier = GlanceModifier.wrapContentSize(),
		color = GlanceTheme.colors.primary,
	)
}