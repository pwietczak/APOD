package eu.pw.apod.designsystem.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ApodSpacing {
	val xsmall: Dp = 2.dp
	val small: Dp = 4.dp
	val medium: Dp = 8.dp
	val large: Dp = 16.dp
}

val LocalApodSPacing =
	compositionLocalOf {
		ApodSpacing
	}