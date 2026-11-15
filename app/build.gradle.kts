plugins {
	id("apod.android.application")
	alias(libs.plugins.dagger.hilt.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.kotlin.serialization)
	alias(libs.plugins.ksp)
}

android {
	namespace = "eu.pw.apod"

	defaultConfig {
		applicationId = "eu.pw.apod"
		targetSdk = 37
		versionCode = 1
		versionName = "1.0"
	}
	buildFeatures {
		compose = true
	}
}

dependencies {
	implementation(project(":designsystem"))
	implementation(project(":data"))
	implementation(project(":domain"))
	implementation(project(":domain:contract"))
	implementation(project(":widget"))
	implementation(project(":widget:contract"))

	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.bundles.compose)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.timber)
	implementation(libs.bundles.coil)
	implementation(libs.splashscreen)

	implementation(libs.bundles.hilt.runtime)
	implementation(libs.hilt.navigation.compose)
	ksp(libs.hilt.compiler)
	ksp(libs.hilt.compiler.androidx)

	implementation(libs.work)

	testImplementation(libs.bundles.unit.test)

	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.bundles.android.test)

	debugImplementation(libs.androidx.compose.ui.test.manifest)
	debugImplementation(libs.androidx.compose.ui.tooling)
}