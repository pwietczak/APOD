---
name: gradle-version-catalog-maintenance
description: Organize and maintain Gradle version catalogs (libs.versions.toml), including topic-based sections, comments, and dependency bundles. Use when asked to clean up or structure a TOML version catalog without removing existing aliases.
---

# Gradle Version Catalog Maintenance

Use this skill only for Gradle version catalog files, typically `gradle/libs.versions.toml`, and any build scripts that need to consume newly added bundles.

## Workflow

1. Read the entire target TOML catalog and identify its existing sections, aliases, versions, comments, and plugin entries.
2. Always inspect the build scripts that consume the catalog and inventory dependency groups used together by module and configuration. Do not limit this review to bundles that already exist or to bundles explicitly requested.
3. Group entries into clear topic-based sections and add short comments that clarify the groups or non-obvious intent.
4. Preserve every existing version, alias, and commented-out entry. Do not remove entries just because they are currently unused.
5. For each inventoried group, explicitly decide whether a bundle is useful. Add bundles for related libraries used together under the same Gradle configuration, including groups confined to one module (for example, a widget module that uses the Glance libraries together). Do not skip a suitable group just because it has no existing bundle.
6. Keep platform/BOM declarations, debug-only dependencies, annotation processors, and dependencies with different scopes out of bundles. Do not create bundles for unrelated libraries merely because they share a configuration.
7. Update every relevant build script to consume new bundles without changing dependency scopes or behavior.
8. Before finishing, compare the catalog bundles with the inventory: confirm every useful same-configuration group has been considered and either bundled or deliberately left separate. State when no additional useful bundles were found.
9. Validate the catalog and affected build, normally by running the smallest relevant Gradle build task.

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
