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

	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.bundles.compose)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(libs.timber)
	implementation(libs.bundles.coil)

	implementation(libs.hilt)
	implementation(libs.hilt.navigation.compose)
	implementation(libs.hilt.work)
	ksp(libs.hilt.compiler)
	ksp(libs.hilt.compiler.androidx)

	implementation(libs.work)

	testImplementation(libs.junit)
	testImplementation(libs.kotlinx.coroutines.test)

	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(libs.androidx.junit)

	debugImplementation(libs.androidx.compose.ui.test.manifest)
	debugImplementation(libs.androidx.compose.ui.tooling)
}