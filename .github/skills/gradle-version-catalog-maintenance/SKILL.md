---
name: gradle-version-catalog-maintenance
description: Organize and maintain Gradle version catalogs (libs.versions.toml), including topic-based sections, comments, and dependency bundles. Use when asked to clean up or structure a TOML version catalog without removing existing aliases.
---

# Gradle Version Catalog Maintenance

Use this skill only for Gradle version catalog files, typically `gradle/libs.versions.toml`, and any build scripts that need to consume newly added bundles.

## Workflow

1. Read the entire target TOML catalog and identify its existing sections, aliases, versions, comments, and plugin entries.
2. If bundles are requested or useful for the requested cleanup, inspect build scripts to see which dependencies are used together and under which Gradle configurations.
3. Group entries into clear topic-based sections and add short comments that clarify the groups or non-obvious intent.
4. Preserve every existing version, alias, and commented-out entry. Do not remove entries just because they are currently unused.
5. Add bundles only for dependencies that are commonly consumed together with the same configuration. Keep platform/BOM declarations, debug-only dependencies, annotation processors, and test dependencies in their appropriate configurations.
6. Update relevant build scripts to consume new bundles without changing the dependency scopes or behavior.
7. Validate the catalog and affected build, normally by running the smallest relevant Gradle build task.

## Guidelines

- Scope work to the version catalog and the build-script references required to adopt bundles. Do not refactor convention plugins or unrelated Gradle configuration.
- Keep all dependency coordinates and version choices unchanged unless the user explicitly requests version updates.
- Do not delete aliases, versions, or commented-out entries that appear unused.
- Avoid bundles that combine dependencies with different Gradle scopes or unrelated purposes.
- Keep comments concise and useful; avoid adding a comment to every individual entry.

## Examples

### Organize entries by topic

Use comments to make entries easy to scan while keeping each TOML table declared only once:

```toml
[versions]
# Android and UI dependency versions
agp = "9.5.1"
composeBom = "2026.02.01"

[libraries]
# Jetpack Compose
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }

# Tests
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
```

In the reviewed project, the catalog was divided into topics such as AndroidX, Compose, Glance, dependency injection, networking, tests, and build-logic dependencies. Existing versions and aliases were retained.

### Add and consume bundles

Bundle libraries that are used together under the same dependency configuration:

```toml
[bundles]
compose = [
    "androidx-activity-compose",
    "androidx-compose-material3",
    "androidx-compose-ui",
]
```

Keep a BOM as a separate platform dependency, then use the bundle under the same configuration as its member dependencies:

```kotlin
dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
}
```

In the reviewed project, Compose UI dependencies were grouped into `compose`, while instrumentation dependencies were grouped into `android-test` and remained under `androidTestImplementation`. Debug-only tooling and the Compose BOM remained separate.
