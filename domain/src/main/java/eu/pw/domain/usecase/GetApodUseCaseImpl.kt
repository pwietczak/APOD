package eu.pw.domain.usecase

import eu.pw.domain.contract.repository.ApodRepository
import eu.pw.domain.contract.usecase.GetApodUseCase
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

internal class GetApodUseCaseImpl(
	private val repository: ApodRepository,
) : GetApodUseCase {
	override suspend operator fun invoke(date: String): GetApodUseCase.Result =
		try {
			repository.getApod(date).fold(
				onSuccess = { apod ->
					GetApodUseCase.Result.Success(apod)
				},
				onFailure = { throwable ->
					Timber.e(throwable, "Error loading Apod")
					throwable.rethrowIfCancelled()
					GetApodUseCase.Result.Failure
				},
			)
		} catch (e: CancellationException) {
			throw e
		} catch (e: Exception) {
			Timber.e(e, "Unexpected error loading Apod")
			GetApodUseCase.Result.Failure
		}

	private fun Throwable.rethrowIfCancelled() {
		if (this is CancellationException) throw this
	}
}