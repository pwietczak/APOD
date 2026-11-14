plugins {
	id("apod.android.optimized-library")
}

android {
	namespace = "eu.pw.apod.domain.contract"
}

dependencies {

	testImplementation(libs.junit)

	androidTestImplementation(libs.androidx.junit)
}