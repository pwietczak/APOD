package eu.pw.apod.presentation.apod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.pw.apod.domain.contract.usecase.GetApodUseCase
import eu.pw.apod.presentation.apod.model.ApodUiMapper
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
internal class ApodViewModel
	@Inject
	constructor(
		private val getApod: GetApodUseCase,
		private val mapper: ApodUiMapper,
	) : ViewModel() {
		private val _uiState = MutableStateFlow<ApodUiState>(ApodUiState.Loading)
		val uiState = _uiState.asStateFlow()

		init {
			loadApod()
		}

		fun loadApod() {
			viewModelScope.launch {
				_uiState.value = ApodUiState.Loading
				val today = getTodayDateForApod()
				val result = getApod(today)
				currentCoroutineContext().ensureActive()
				when (result) {
					is GetApodUseCase.Result.Success -> {
						val apodUi = mapper.map(result.apod)
						_uiState.value = ApodUiState.Success(apodUi)
					}
					GetApodUseCase.Result.Failure -> {
						_uiState.value = ApodUiState.Failure
					}
				}
			}
		}

		private fun getTodayDateForApod() = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))
	}