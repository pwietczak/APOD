package eu.pw.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.pw.data.repository.ApodRepositoryImpl
import eu.pw.domain.contract.repository.ApodRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
	@Singleton
	@Binds
	abstract fun bindRepository(repository: ApodRepositoryImpl): ApodRepository
}