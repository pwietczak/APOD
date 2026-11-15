plugins {
	id("apod.android.optimized-library")
	alias(libs.plugins.dagger.hilt.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.ksp)
}

android {
	namespace = "eu.pw.apod.widget"
}

dependencies {

	implementation(project(":domain:contract"))
	implementation(project(":widget:contract"))
	implementation(project(":designsystem"))

	implementation(libs.androidx.core.ktx)
	implementation(libs.bundles.compose.ui)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.datastore.preferences)
	implementation(libs.timber)

	implementation(libs.bundles.moshi)
	ksp(libs.moshi.codegen)

	implementation(libs.bundles.hilt.runtime)
	ksp(libs.hilt.compiler)
	ksp(libs.hilt.compiler.androidx)

	implementation(libs.work)

	implementation(libs.bundles.glance)

	testImplementation(libs.junit)

	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.bundles.android.test)

	debugImplementation(libs.androidx.compose.ui.test.manifest)
	debugImplementation(libs.androidx.compose.ui.tooling)
}