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
- Do not hardcode colors; use `AppTheme.colors`.

## 🧪 Testing Standards
- **Unit Tests:** Mandatory for all ViewModels and Domain logic.
- **Location:** Place tests in the corresponding `test/` folder of the module.
- **Style:** Use JUnit 5 or 4 (as established in the project) and MockK/Truth for assertions.

## 🧰 Tooling
- Always use `android layout --pretty` before modifying the calculator UI to understand the current composable tree.
- Run `android describe` to view the latest module dependencies.

## 🤖 Agent Personas & Skills
This project leverages custom subagent personas and skills specifically structured to assist AI agents in specialized tasks.

### 👥 Subagent Personas (located in `.agents/agents/`)
- **[Code Reviewer](.agents/agents/CODE_REVIEWER_AGENT.md):** Reviews code changes for correctness, styling rules, Compose best practices, and architectural compliance.
- **[Gradle Update Agent](.agents/agents/GRADLE_UPDATE_AGENT.md):** Monitors dependency updates, maintains version catalogs, and keeps build scripts modular.
- **[UI QA Agent](.agents/agents/UI_QA_AGENT.md):** Launches emulators, runs visual regression tests, and executes natural language user journey tests.

### 🛠️ Custom Skills (located in `.agents/skills/`)
- **[android-cli](.agents/skills/android-cli/SKILL.md):** Orchestrates SDK installation, emulator boot/management, screenshot captures, and layout nesting analysis.
- **[audit-documentation](.agents/skills/audit-documentation/SKILL.md):** Main guide to check layout changes, verify file URLs, and maintain system documentation.
- **[scaffold-feature](.agents/skills/scaffold-feature/SKILL.md):** Templates and structure to quickly generate new MVVM feature modules.
- **[validate-architecture](.agents/skills/validate-architecture/SKILL.md):** Diagnostic checks verifying Hilt DI, compose themes, state flows, and navigation compliance.

## 🔒 Security
- Never log or commit secrets, API keys, or sensitive credentials.

---
*This file is linked from LLM-specific entry points (e.g., CLAUDE.md).*
