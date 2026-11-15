package eu.pw.apod.data.repository

import eu.pw.apod.data.datasource.ApodDataSource
import eu.pw.apod.data.mapper.ApodMapper
import eu.pw.apod.domain.contract.model.Apod
import eu.pw.apod.domain.contract.repository.ApodRepository
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