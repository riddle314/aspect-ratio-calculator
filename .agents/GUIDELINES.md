# AI Agent Development Guidelines

This document serves as the primary source of truth for all AI agents and LLMs working on the **Aspect Ratio Calculator** project. Adhere to these principles for all code generation and refactoring.

## 🏗 Core Architecture
- **Pattern:** MVVM (Model-View-ViewModel) with Unidirectional Data Flow (UDF).
- **State Management:** Use `StateFlow` in ViewModels to expose UI state. Use `Channels` for one-time side effects (navigation, snackbars).
- **Modularity:** Strictly follow the feature-based modular structure:
    - `:app`: Entry point and Hilt configuration.
    - `:feature:*`: Isolated functional modules.
    - `:core:designsystem`: All UI components and design tokens.
    - `:core:navigation`: Centralized, type-safe navigation logic.
    - `:core:common`: Reusable non-UI utilities.

## 🛠 Tech Stack Mandates
- **Language:** 100% Kotlin.
- **UI:** Jetpack Compose (Declarative). No XML Views for new features.
- **Dependency Injection:** Hilt. Ensure all ViewModels and repository-level classes are properly annotated.
- **Navigation:** AndroidX Navigation 3. Use type-safe route definitions.
- **Build System:** Gradle Kotlin DSL (`.gradle.kts`). 
    - **Note:** Centralized build logic is managed in `build-logic/`. Avoid adding heavy logic to individual `build.gradle.kts` files.

## 🎨 UI & Design System
- Always use components and theme tokens from `:core:designsystem`.
- Do not hardcode colors or dimensions; use `AppTheme.colors` and `AppTheme.spacing`.

## 🧪 Testing Standards
- **Unit Tests:** Mandatory for all ViewModels and Domain logic.
- **Location:** Place tests in the corresponding `test/` folder of the module.
- **Style:** Use JUnit 5 or 4 (as established in the project) and MockK/Truth for assertions.

## 🧰 Tooling
- The `.agents/skills` directory contains standard capabilities.
- Always use `android layout --pretty` before modifying the calculator UI to understand the current composable tree.
- Run `android describe` to view the latest module dependencies.

## 🔒 Security
- Never log or commit secrets, API keys, or sensitive credentials.

---
*This file is linked from LLM-specific entry points (GEMINI.md, .claude/README.md).*
