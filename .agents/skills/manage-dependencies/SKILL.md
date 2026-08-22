---
name: manage-dependencies
description: >-
  Comprehensive guide for managing the full dependency lifecycle: adding new dependencies,
  declaring version catalog entries (libs.versions.toml), analyzing upgrades, risk-tiering,
  compatibility matrix validation, and pruning unused libraries.
metadata:
  author: Aspect Ratio Calculator Team
  keywords:
  - dependencies
  - version-catalog
  - gradle
  - upgrade
  - libraries
  - build
  - dependabot
---

# Manage Dependencies Skill

This skill provides a comprehensive guide for managing the full dependency lifecycle within the **Aspect Ratio Calculator** project. It outlines best practices for adding new dependencies to the centralized Version Catalog (`gradle/libs.versions.toml`), selecting proper dependency configurations, assessing upgrade risk tiers, validating compatibility matrices, maintaining Dependabot groupings, and pruning unused dependencies.

---

## 🛠️ Toolkit Context & Commands

1. **Version Lookup**:
   - `android studio version-lookup`: Look up the latest available versions of Maven artifacts, dependencies, and plugin updates.
2. **Gradle Verification**:
   - `./gradlew assembleDebug`: Compile and assemble debug APK to check compilation across all modules.
   - `./gradlew check`: Run static analysis, linting, and unit tests across all modules.
   - `./gradlew help`: Explore available Gradle tasks.

---

## 📋 Lifecycle Workflows

### 1. Adding New Dependencies

When introducing a new library or dependency to the project:

1. **Inspect Version Catalog (`gradle/libs.versions.toml`)**:
   - Check whether the library group or related dependencies already exist under `[versions]` and `[libraries]`.
2. **Declare in `gradle/libs.versions.toml`**:
   - Add the version definition under `[versions]`:
     ```toml
     [versions]
     example-lib = "1.2.0"
     ```
   - Add the library definition under `[libraries]`:
     ```toml
     [libraries]
     example-lib = { group = "com.example", name = "example-lib", version.ref = "example-lib" }
     ```
   - If introducing a Gradle plugin, declare it under `[plugins]`:
     ```toml
     [plugins]
     example-plugin = { id = "com.example.plugin", version.ref = "example-lib" }
     ```
   - If multiple libraries are always used together, consider creating a bundle under `[bundles]`:
     ```toml
     [bundles]
     example-bundle = ["example-lib", "example-lib-ext"]
     ```
3. **Select the Correct Dependency Configuration**:
   - `implementation`: Default for internal implementation details not exposed through the public API of the module.
   - `api`: Only when transitive dependencies must be exposed to dependent modules (use sparingly).
   - `compileOnly`: For compile-time-only dependencies (e.g., annotations not needed at runtime).
   - `testImplementation`: For unit tests running on the JVM (`src/test/`).
   - `androidTestImplementation`: For on-device or instrumentation tests (`src/androidTest/`).
4. **Reference in `build.gradle.kts`**:
   - Use the type-safe catalog accessor (e.g., `implementation(libs.example.lib)` or `implementation(libs.bundles.example.bundle)`).
   - **Never hardcode raw artifact coordinate strings** (e.g. `implementation("com.example:example-lib:1.2.0")`) in module `build.gradle.kts` files.

---

### 2. Upgrading Existing Dependencies

When analyzing, modifying, or upgrading dependencies:

1. **Version Discovery**:
   - Check current versions in `gradle/libs.versions.toml`.
   - Discover updates via `android studio version-lookup` or official Maven repository listings.
2. **Risk Assessment & Mitigation**:
   - **Major Updates (`X.y.z` -> `Y.a.b`)**:
     - **Review Release Notes**: Check official release notes and changelogs first for breaking API changes, behavioral adjustments, or deprecations.
     - **Proactive Refactoring**: Search the codebase (via `grep_search`) for affected classes, methods, or parameters, and update the codebase to match the new API.
   - **Minor Updates (`x.Y.z` -> `x.A.b`)**:
     - Check release notes for core framework libraries (Kotlin, Compose, Hilt, Navigation, AGP).
     - For standard utility libraries, apply the update and verify via test runs.
   - **Patch Updates (`x.y.Z` -> `x.y.C`)**:
     - Apply directly and verify with build runs.

---

### 3. Compatibility Matrix Validation

Interdependent platform and tooling dependencies must be updated in lockstep:

* **Kotlin & KSP**: Ensure the KSP plugin version matches the Kotlin compiler version (`kotlin` <-> `ksp`).
* **Kotlin & Compose Compiler**: Verify the Jetpack Compose compiler extension / Compose plugin is compatible with the Kotlin version.
* **Android Gradle Plugin (AGP) & Gradle Wrapper**: Ensure the distribution URL in `gradle/wrapper/gradle-wrapper.properties` supports the AGP version.
* **AndroidX Navigation 3 & Lifecycle**: Verify compatibility between Navigation 3, Lifecycle ViewModel, and Compose runtime artifacts.

---

### 4. Dependabot & Grouping Alignment

When adding core libraries, ensure `.github/dependabot.yml` maintains proper grouping:
* Group updates logically (e.g., `compose`, `kotlin`, `androidx`, `hilt`, `testing`) to prevent fragmented automated pull requests.

---

### 5. Pruning & Dead Dependency Removal

Periodically or after refactoring:
1. Inspect `gradle/libs.versions.toml` to find any declared libraries, plugins, or bundles that are no longer referenced in any `build.gradle.kts` file.
2. Remove obsolete entries and unused version variables to keep the catalog lean.

---

### 6. Build Verification

Always execute verification after modifying dependencies:
1. Check compilation across all modules:
   ```bash
   ./gradlew assembleDebug
   ```
2. Run test and compliance suite:
   ```bash
   ./gradlew check
   ```
3. **Handling Failures**:
   - If the build fails, examine the compiler/dependency error log.
   - Cross-reference errors with library migration guides.
   - If blockers or unresolvable conflicts arise, revert the version in `gradle/libs.versions.toml` and document the issue.
