package eu.pw.domain.contract.repository

import eu.pw.domain.contract.model.Apod

interface ApodRepository {
	suspend fun getApod(date: String): Result<Apod>
}