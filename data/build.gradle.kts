plugins {
	id("apod.android.optimized-library")
	alias(libs.plugins.dagger.hilt.android)
	alias(libs.plugins.ksp)
}

android {
	namespace = "eu.pw.apod.data"
}

dependencies {
	implementation(project(":domain:contract"))

	implementation(libs.moshi.kotlin)
	implementation(libs.ktor.client.android)
	implementation(libs.timber)
	implementation(libs.jsoup)

	implementation(libs.hilt)
	ksp(libs.hilt.compiler)
	ksp(libs.hilt.compiler.androidx)
	ksp(libs.moshi.codegen)

	testImplementation(libs.junit)
	testImplementation(libs.ktor.client.mock)
	testImplementation(libs.kotlinx.coroutines.test)

	androidTestImplementation(libs.androidx.junit)
}