---
name: validate-architecture
description: Analyzes project code to ensure compliance with the Aspect Ratio Calculator architecture guidelines (MVVM, UDF, Jetpack Compose, Hilt, modularity).
metadata:
  author: Aspect Ratio Calculator Team
  keywords:
  - architecture
  - linter
  - guidelines
  - QA
  - checks
  - mvvm
  - compose
---
# Validate Architecture Skill

This skill provides instructions for AI agents on how to validate the architecture of code within the Aspect Ratio Calculator project. Use this skill when asked to review code, validate a new feature, or check architecture compliance.

## Pre-requisites
Before validating, ensure you have read `AGENTS.md` to understand the project's core architecture rules.

## Validation Checklist

When asked to validate a module, file, or feature, you must perform the following checks:

### 1. Modularity & Dependencies
- **Feature Modules:** Is the code placed in the correct module? (e.g., `:app`, `:feature:*`, `:core:designsystem`, `:core:navigation`, `:core:common`).
- **Build System:** Is `build.gradle.kts` using centralized build logic (`build-logic/`) instead of hardcoding plugin versions and complex logic?

### 2. UI & Jetpack Compose
- **No XML:** Ensure no XML layout files (`.xml` inside `res/layout`) were created for new features. 100% Jetpack Compose is required.
- **Design System:** Ensure all colors and typography use `AppTheme` (e.g., `AppTheme.colors`, `AppTheme.typography`) from `:core:designsystem`. No hardcoded hex colors.

### 3. Architecture (MVVM + UDF)
- **ViewModel:** Does the ViewModel exist and is it annotated with `@HiltViewModel`?
- **StateFlow:** Is the UI state exposed via `StateFlow`?
- **Channels:** Are one-time side effects (like navigation or snackbars) handled via `Channels`?
- **Business Logic:** Is business logic kept out of the UI layer (Composables) and placed in the ViewModel or Domain layer?

### 4. Dependency Injection (Hilt)
- **Annotations:** Are necessary Hilt annotations present? (`@AndroidEntryPoint` on Activities, `@Inject constructor` on ViewModels/Repositories).

### 5. Navigation
- **Navigation 3:** Is AndroidX Navigation 3 used with type-safe route definitions? 

### 6. Testing
- **Unit Tests:** Do unit tests exist for ViewModels and domain logic in the `test/` directory?
- **Frameworks:** Are they using JUnit (4 or 5), MockK, and Truth for assertions?

## Execution Workflow
1. Use file viewing or search tools to inspect the files in the targeted module or feature.
2. Specifically `grep_search` or look for hardcoded colors (e.g., `Color(0xFF`), and XML usages.
3. Review `build.gradle.kts` for proper modularity and usage of `build-logic`.
4. Review the ViewModel source code for `StateFlow`, `Channel`, and `@HiltViewModel`.
5. Report a summary of violations to the user and provide actionable refactoring steps to fix them.
