package eu.pw.apod.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.pw.apod.data.mapper.ApodMapper
import eu.pw.apod.data.mapper.ApodMapperImpl
import eu.pw.apod.data.mapper.HtmlTextSanitizer
import eu.pw.apod.data.mapper.HtmlTextSanitizerImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MapperModule {
	@Binds
	abstract fun bindApodMapper(mapper: ApodMapperImpl): ApodMapper

	@Binds
	abstract fun bindHtmlTextSanitizer(sanitizer: HtmlTextSanitizerImpl): HtmlTextSanitizer
}