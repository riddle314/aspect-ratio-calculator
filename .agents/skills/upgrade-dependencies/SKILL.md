---
name: upgrade-dependencies
description: >-
  Step-by-step guide for analyzing, upgrading, and verifying dependencies and version catalogs (libs.versions.toml) safely with risk mitigation and cross-referencing.
metadata:
  author: Aspect Ratio Calculator Team
  keywords:
  - dependencies
  - version-catalog
  - gradle
  - upgrade
  - libraries
  - build
---

# Upgrade Dependencies Skill

This skill provides a comprehensive, step-by-step guide for checking, upgrading, and verifying dependencies within the Aspect Ratio Calculator project. It defines risk assessment tiers, cross-referencing procedures for breaking changes, and build verification steps.

---

## 🛠️ Toolkit Context & Commands

1. **Android Studio & CLI Version Lookup**:
   - `android studio version-lookup`: Look up the latest available versions on the internet of Maven artifacts, dependencies, and plugin updates.
2. **Gradle Verification**:
   - `./gradlew assembleDebug`: Compile and assemble debug APK to check compilation.
   - `./gradlew check`: Run static analysis, linting, and unit tests across all modules.
   - `./gradlew help`: Explore available Gradle tasks.

---

## 📋 Dependency Upgrade Workflow

When analyzing, modifying, or upgrading dependencies in `gradle/libs.versions.toml`:

### 1. Version Discovery & Catalog Inspection
* Read `gradle/libs.versions.toml` to inspect current versions defined under `[versions]`.
* Check for newer releases of core libraries (e.g., AndroidX, Hilt, Jetpack Compose, Kotlin, AGP, Coroutines, Serialization, JUnit, etc.) using `android studio version-lookup` or Maven repository lookups.

### 2. Upgrade Risk Assessment & Mitigation
Before applying changes to `libs.versions.toml`, categorize the upgrade by risk tier:

* **Major Updates (`X.y.z` -> `Y.a.b`)**:
  - **Review Release Notes**: Look up official release notes/changelogs first to identify breaking changes, deprecated/removed APIs, behavior changes, or required migration steps.
  - **Cross-Reference & Proactive Refactoring**: Search the codebase (via `grep_search` or symbol search) for any classes, methods, or parameters affected by the breaking changes or deprecations. If they are used, proactively refactor the codebase to match the new version's API requirements.
* **Minor Updates (`x.Y.z` -> `x.A.b`)**:
  - Check release notes/changelogs for core platform libraries (e.g., Kotlin, Gradle/AGP, Jetpack Compose, Hilt) or if compilation or tests fail.
  - For standard utility libraries, apply the update and verify via test runs.
* **Patch Updates (`x.y.Z` -> `x.y.C`)**:
  - Apply directly without checking release notes unless compilation or tests fail.

### 3. Compatibility Matrix Validation
Ensure interdependent libraries are updated in lockstep according to official compatibility matrices:
* **Kotlin & KSP**: Verify KSP plugin version matches the target Kotlin compiler version (`kotlin` <-> `ksp`).
* **Kotlin & Compose Compiler**: Ensure Compose plugin/compiler is compatible with the Kotlin version.
* **Android Gradle Plugin (AGP) & Gradle Wrapper**: Ensure the Gradle wrapper distribution URL in `gradle/wrapper/gradle-wrapper.properties` supports the AGP version.
* **Navigation 3 & Lifecycle**: Verify AndroidX Navigation 3 and Lifecycle artifact compatibility.

### 4. Applying & Verifying Updates
1. Safely increment target versions in `gradle/libs.versions.toml`.
2. Run compilation verification:
   ```bash
   ./gradlew assembleDebug
   ```
3. Run test and compliance suite:
   ```bash
   ./gradlew check
   ```
4. **Handling Failures**:
   - If the build fails, examine the compiler/dependency error log.
   - Cross-reference the error with library release notes.
   - If unresolvable or incompatible with other core libraries, revert the version in `libs.versions.toml` and document the blocker.
