package eu.pw.apod.presentation.apod

import eu.pw.apod.domain.contract.model.Apod
import eu.pw.apod.domain.contract.model.Image
import eu.pw.apod.domain.contract.usecase.GetApodUseCase
import eu.pw.apod.presentation.apod.model.ApodUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ApodViewModelTest {
	private val mainDispatcher = StandardTestDispatcher()

	@Before
	fun setUp() {
		Dispatchers.setMain(mainDispatcher)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun `loadApod publishes mapped data on success`() =
		runTest(mainDispatcher) {
			var requestedDate: String? = null
			val viewModel =
				ApodViewModel(
					getApod =
						object : GetApodUseCase {
							override suspend fun invoke(date: String): GetApodUseCase.Result {
								requestedDate = date
								return GetApodUseCase.Result.Success(
									Apod(
										date = "2026-11-14",
										title = "The Cosmos",
										postUrl = "https://science.nasa.gov/image-article/apod-2026-november-14/",
										credit = "NASA",
										explanation = "A view of the cosmos.",
										image = Image(alt = "Stars", url = "https://example.com/image.jpg"),
									),
								)
							}
						},
					mapper = ApodUiMapper(),
				)

			advanceUntilIdle()

			assertTrue(requestedDate.orEmpty().matches(Regex("\\d{6}")))
			val state = viewModel.uiState.value
			assertTrue(state is ApodUiState.Success)
			assertEquals("The Cosmos", (state as ApodUiState.Success).apod.title)
			assertEquals("2026-11-14", state.apod.date)
			assertEquals("https://science.nasa.gov/image-article/apod-2026-november-14/", state.apod.postUrl)
			assertEquals("https://example.com/image.jpg", state.apod.imageUi?.url)
		}

	@Test
	fun `loadApod publishes failure from use case`() =
		runTest(mainDispatcher) {
			val viewModel =
				ApodViewModel(
					getApod =
						object : GetApodUseCase {
							override suspend fun invoke(date: String) = GetApodUseCase.Result.Failure
						},
					mapper = ApodUiMapper(),
				)

			advanceUntilIdle()

			assertEquals(ApodUiState.Failure, viewModel.uiState.value)
		}

	@Test
	fun `loadApod cancels previous request and publishes latest response`() =
		runTest(mainDispatcher) {
			var requestCount = 0
			var firstRequestCancelled = false
			val viewModel =
				ApodViewModel(
					getApod =
						object : GetApodUseCase {
							override suspend fun invoke(date: String): GetApodUseCase.Result {
								requestCount++
								if (requestCount == 1) {
									try {
										awaitCancellation()
									} finally {
										firstRequestCancelled = true
									}
								}
								return GetApodUseCase.Result.Success(
									Apod(
										date = "2026-11-14",
										title = "Latest Apod",
										postUrl = null,
										credit = null,
										explanation = null,
										image = Image(alt = null, url = null),
									),
								)
							}
						},
					mapper = ApodUiMapper(),
				)

			runCurrent()
			viewModel.loadApod()
			advanceUntilIdle()

			assertTrue(firstRequestCancelled)
			assertEquals(2, requestCount)
			assertEquals("Latest Apod", (viewModel.uiState.value as ApodUiState.Success).apod.title)
		}
}