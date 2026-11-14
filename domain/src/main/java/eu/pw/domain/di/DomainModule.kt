package eu.pw.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.pw.domain.contract.repository.ApodRepository
import eu.pw.domain.contract.usecase.GetApodUseCase
import eu.pw.domain.usecase.GetApodUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
	@Provides
	fun bindApodUseCase(apodRepository: ApodRepository): GetApodUseCase =
		GetApodUseCaseImpl(apodRepository)
}