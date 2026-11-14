package eu.pw.data.repository

import eu.pw.data.datasource.ApodDataSource
import eu.pw.data.mapper.ApodMapper
import eu.pw.domain.contract.model.Apod
import eu.pw.domain.contract.repository.ApodRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

internal class ApodRepositoryImpl
	@Inject
	constructor(
		private val remoteDataSource: ApodDataSource,
		private val mapper: ApodMapper,
	) : ApodRepository {
		override suspend fun getApod(date: String): Result<Apod> =
			runCatching {
				val dto = remoteDataSource.getApod(date)
				mapper.toDomain(dto)
			}.onFailure { throwable ->
				if (throwable is CancellationException) throw throwable
			}
	}