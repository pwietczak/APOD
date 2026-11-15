plugins {
	id("apod.android.library")
	alias(libs.plugins.kotlin.compose)
}

android {
	namespace = "eu.pw.apod.designsystem"
}

dependencies {
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.bundles.compose)
	api(libs.androidx.compose.material3)
	implementation(libs.androidx.compose.ui.graphics)

	debugImplementation(libs.androidx.compose.ui.test.manifest)
	debugImplementation(libs.androidx.compose.ui.tooling)
}