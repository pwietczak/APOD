package eu.pw.domain.usecase

import eu.pw.domain.contract.model.Apod
import eu.pw.domain.contract.repository.ApodRepository
import eu.pw.domain.contract.usecase.GetApodUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertSame
import org.junit.Assert.fail
import org.junit.Test

class GetApodUseCaseImplTest {
	@Test
	fun `repository failure is returned without exposing exception message`() =
		runTest {
			val useCase =
				GetApodUseCaseImpl(
					repositoryReturning(Result.failure<Apod>(IllegalStateException("internal detail"))),
				)

			assertSame(GetApodUseCase.Result.Failure, useCase("261114"))
		}

	@Test
	fun `unexpected repository exception is returned as generic failure`() =
		runTest {
			val useCase =
				GetApodUseCaseImpl(
					object : ApodRepository {
						override suspend fun getApod(date: String): Result<Apod> =
							throw IllegalStateException("internal detail")
					},
				)

			assertSame(GetApodUseCase.Result.Failure, useCase("261114"))
		}

	@Test
	fun `cancellation exception is propagated`() =
		runTest {
			val cancellation = CancellationException("cancelled")
			val useCase = GetApodUseCaseImpl(repositoryReturning(Result.failure<Apod>(cancellation)))

			try {
				useCase("261114")
				fail("Expected cancellation to be propagated")
			} catch (actual: CancellationException) {
				assertSame(cancellation, actual)
			}
		}

	private fun repositoryReturning(result: Result<Apod>) =
		object : ApodRepository {
			override suspend fun getApod(date: String): Result<Apod> = result
		}
}