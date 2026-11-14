package eu.pw.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.pw.data.datasource.ApodDataSource
import eu.pw.data.datasource.ApodRemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
	@Binds
	abstract fun bindApodDataSource(remoteDataSource: ApodRemoteDataSource): ApodDataSource
}