package eu.pw.apod.data.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	@Provides
	@Singleton
	fun provideMoshi(): Moshi = Moshi.Builder().build()

	@Provides
	@Singleton
	fun provideHttpClient(): HttpClient =
		HttpClient(Android) {
			expectSuccess = true
			install(HttpTimeout) {
				requestTimeoutMillis = 15_000
				connectTimeoutMillis = 10_000
				socketTimeoutMillis = 15_000
			}
		}
}