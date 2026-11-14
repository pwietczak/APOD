package eu.pw.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.pw.data.mapper.ApodMapper
import eu.pw.data.mapper.ApodMapperImpl
import eu.pw.data.mapper.HtmlTextSanitizer
import eu.pw.data.mapper.HtmlTextSanitizerImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MapperModule {
	@Binds
	abstract fun bindApodMapper(mapper: ApodMapperImpl): ApodMapper

	@Binds
	abstract fun bindHtmlTextSanitizer(sanitizer: HtmlTextSanitizerImpl): HtmlTextSanitizer
}