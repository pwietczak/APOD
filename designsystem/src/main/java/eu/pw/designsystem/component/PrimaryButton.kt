package eu.pw.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import eu.pw.designsystem.ui.theme.ApodTheme

@Composable
fun PrimaryButton(
	modifier: Modifier,
	text: String,
	onClick: () -> Unit,
	enabled: Boolean = true,
) {
	Button(
		onClick = onClick,
		enabled = enabled,
		modifier = modifier,
		shape = RoundedCornerShape(8.dp),
		colors =
			ButtonDefaults.buttonColors(
				containerColor = ApodTheme.color.primary,
				contentColor = ApodTheme.color.onPrimary,
				disabledContainerColor = ApodTheme.color.surfaceVariant,
				disabledContentColor = ApodTheme.color.onSurfaceVariant,
			),
	) {
		Text(
			text = text,
			style = ApodTheme.typography.bodyLarge,
			modifier = Modifier.padding(horizontal = ApodTheme.spacing.large),
			maxLines = 1,
		)
	}
}

@Composable
@PreviewLightDark
private fun PrimaryButtonPreview() {
	ApodTheme {
		PrimaryButton(modifier = Modifier.fillMaxWidth(), text = "Action", onClick = {})
	}
}

@Composable
@PreviewLightDark
private fun PrimaryButtonDisabledPreview() {
	ApodTheme {
		PrimaryButton(
			modifier = Modifier.fillMaxWidth(),
			text = "Action",
			onClick = {},
			enabled = false,
		)
	}
}