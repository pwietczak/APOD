# Repository guide

## Build, test, and lint

Use the Gradle wrapper from the repository root (`.\gradlew.bat` on Windows, `./gradlew` on macOS/Linux):

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat testDebugUnitTest
.\gradlew.bat :app:testDebugUnitTest --tests "eu.pw.apod.presentation.apod.ApodViewModelTest"
.\gradlew.bat ktlintCheck
.\gradlew.bat :app:lintDebug
```

Replace `.\gradlew.bat` with `./gradlew` on macOS/Linux. Unit tests live in the `app`, `data`, and `domain` modules; target a specific module's `testDebugUnitTest` task and pass `--tests "<fully.qualified.TestClass>"` to run one test class. Use `ktlintFormat` to apply Kotlin formatting. Android modules wire `ktlintFormat` into `preBuild`, so builds may format Kotlin sources.

The Gradle daemon JVM is configured by `gradle/gradle-daemon-jvm.properties`; Android compilation targets Java 21. Dependency and plugin versions are centralized in `gradle/libs.versions.toml`. For version-catalog edits, follow `.github/skills/gradle-version-catalog-maintenance/SKILL.md`.

## Architecture

This is a multi-module Android app. `:app` owns the Compose screen and ViewModel; `:designsystem` provides shared Compose components and theme; `:data` implements network access, DTO-to-domain mapping, and repository behavior; `:domain` implements use cases. The `:domain:contract` module contains the domain model and the repository/use-case interfaces consumed across these layers. `:widget` is a separate Android Glance app-widget module.

The APOD flow is wired with Hilt: the data layer binds its data source, mappers, and repository to interfaces, and the domain layer provides `GetApodUseCase`. The remote source requests NASA's APOD content, decodes it with Moshi over Ktor, then the data mapper sanitizes HTML text while converting to domain models. The app maps domain models to UI models, exposes loading/success/failure through a `StateFlow`, and renders those states in Compose.

## Repository-specific conventions

- Keep module dependencies pointing inward to contracts: domain interfaces and models belong in `:domain:contract`, while implementations stay in `:domain` or `:data`.
- Preserve the result and cancellation flow: repository operations return Kotlin `Result`, the use case translates outcomes to `GetApodUseCase.Result`, and `CancellationException` must propagate rather than becoming an ordinary failure.
- The APOD request date is currently formatted as `yyMMdd` by the ViewModel; the returned APOD date is represented separately in the domain model.
- Unit tests are colocated by module under `src/test`. Data-source HTTP tests use Ktor's `MockEngine`; coroutine/ViewModel tests use `kotlinx-coroutines-test` and a test Main dispatcher.
- Kotlin formatting is configured in `.editorconfig` and enforced with the shared `apod.ktlint` convention plugin from `build-logic`; Compose function names are exempted from ktlint's function-naming rule.
