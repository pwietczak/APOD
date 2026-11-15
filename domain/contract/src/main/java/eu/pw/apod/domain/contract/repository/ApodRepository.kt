package eu.pw.apod.domain.contract.repository

import eu.pw.apod.domain.contract.model.Apod

interface ApodRepository {
	suspend fun getApod(date: String): Result<Apod>
}