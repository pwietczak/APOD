package eu.pw.apod.domain.contract.usecase

import eu.pw.apod.domain.contract.model.Apod

interface GetApodUseCase {
	sealed class Result {
		data class Success(
			val apod: Apod,
		) : Result()

		data object Failure : Result()
	}

	suspend operator fun invoke(date: String): Result
}