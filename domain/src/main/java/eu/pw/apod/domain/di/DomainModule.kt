package eu.pw.apod.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.pw.apod.domain.contract.repository.ApodRepository
import eu.pw.apod.domain.contract.usecase.GetApodUseCase
import eu.pw.apod.domain.usecase.GetApodUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
	@Provides
	fun bindApodUseCase(apodRepository: ApodRepository): GetApodUseCase =
		GetApodUseCaseImpl(apodRepository)
}